# Database Setup

This directory contains database setup files for the Insight Backend application.

## Files

- `setup.sql` - Complete PostgreSQL database setup script

## Database Configuration

The application is configured to connect to:
- **Database**: `duality_db`
- **Host**: `localhost:5432`
- **Username**: `postgres`
- **Password**: `Ed0verlo.narva`

## Complete Setup Guide (macOS + PostgreSQL 17)

### 1. Install PostgreSQL 17

```bash
# Install PostgreSQL via Homebrew
brew install postgresql

# Start PostgreSQL service
brew services start postgresql

# Verify installation
psql --version
```

### 2. Create PostgreSQL User and Set Password

```bash
# Create postgres superuser (macOS doesn't have this by default)
createuser -s postgres

# Set password for postgres user
psql postgres -c "ALTER USER postgres PASSWORD 'Ed0verlo.narva';"
```

### 3. Create the Database

```bash
# Create duality_db database
psql -U postgres -c "CREATE DATABASE duality_db;"
```

### 4. Run the Setup Script

```bash
# Execute the database setup script
psql -h localhost -U postgres -d duality_db -f database/setup.sql
```

### 5. Verify the Setup

```bash
# List all tables
psql -h localhost -U postgres -d duality_db -c "\dt"

# Describe a specific table
psql -h localhost -U postgres -d duality_db -c "\d users"

# Check sample data
psql -h localhost -U postgres -d duality_db -c "SELECT * FROM users;"
```

## Expected Output

After running the setup script, you should see:

```
CREATE EXTENSION
CREATE TABLE
CREATE TABLE
CREATE TABLE
CREATE TABLE
CREATE INDEX
CREATE INDEX
CREATE INDEX
CREATE INDEX
CREATE INDEX
CREATE INDEX
CREATE INDEX
CREATE INDEX
CREATE FUNCTION
CREATE TRIGGER
CREATE TRIGGER
CREATE TRIGGER
INSERT 0 1
INSERT 0 3
INSERT 0 1
INSERT 0 1
```

And when listing tables:

```
              List of relations
 Schema |       Name       | Type  |  Owner   
--------+------------------+-------+----------
 public | concepts         | table | postgres
 public | insight_concepts | table | postgres
 public | insights         | table | postgres
 public | users            | table | postgres
```

## Alternative: Using Your macOS Username

If you prefer to use your macOS username instead of creating a postgres user:

```bash
# Create database with your username
createdb duality_db

# Run setup script
psql -d duality_db -f database/setup.sql

# Update application.yml to use your username instead of postgres
```

## Schema Overview

The database contains the following tables:

- **users** - User accounts with username and email
- **insights** - User-generated insights/content
- **concepts** - Knowledge concepts with metadata (synonyms, related terms, etc.)
- **insight_concepts** - Many-to-many relationship between insights and concepts

## Features

- UUID primary keys with auto-generation
- Proper foreign key constraints with CASCADE deletes
- Array columns for concept metadata (synonyms, related terms, antonyms, languages)
- Automatic timestamps (created_at, updated_at)
- Triggers for automatic updated_at column updates
- Performance indexes
- Sample data for testing
- PostgreSQL 17 compatible

## Sample Data Included

The setup script includes sample data:

- **User**: `testuser` with email `test@example.com`
- **Concepts**: Machine Learning, Data Science, Artificial Intelligence
- **Insight**: Sample insight about machine learning
- **Associations**: Sample insight-concept relationships

## Troubleshooting

### Permission Issues
```bash
# If you get permission errors, try:
psql -U $(whoami) -d duality_db
```

### Service Issues
```bash
# Restart PostgreSQL service
brew services restart postgresql

# Check service status
brew services list | grep postgresql
```

### Connection Issues
```bash
# Check if PostgreSQL is running
pg_isready

# Check listening ports
lsof -i :5432
```

### User Already Exists Error
```bash
# If postgres user already exists, skip creation and just set password:
psql postgres -c "ALTER USER postgres PASSWORD 'Ed0verlo.narva';"
```

### Database Already Exists Error
```bash
# If duality_db already exists, drop and recreate:
psql -U postgres -c "DROP DATABASE IF EXISTS duality_db;"
psql -U postgres -c "CREATE DATABASE duality_db;"
```

## Testing the Connection

Once setup is complete, you can test the connection:

```bash
# Test connection with your application credentials
psql -h localhost -U postgres -d duality_db -c "SELECT version();"
```

## Alternative: Using Hibernate DDL

If you prefer to let Hibernate create the schema automatically, you can temporarily change your `application.yml`:

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: create  # Change from 'validate' to 'create'
```

Then run your application once to create the tables, and change it back to `validate`.

## Next Steps

After database setup is complete:

1. **Start your Spring Boot application**:
   ```bash
   ./mvnw spring-boot:run
   ```

2. **Test the API endpoints** using your preferred tool (Postman, curl, etc.)

3. **Verify database connectivity** by checking application logs for successful database connections 