# Load the rails application
require File.expand_path('../application', __FILE__)

# Needed for the local case for asset precompilation, db migration, etc.
# Please comment these two lines out when deploying on SAP HANA Cloud
# Note: This is fixed and no longer required since AR-JDBC Adapter v. 1.2.6
# 
# require 'jdbc/sqlite3'
# Jdbc::SQLite3.load_driver(:require) if Jdbc::SQLite3.respond_to?(:load_driver)

# Initialize the rails application
Photoapp::Application.initialize!
