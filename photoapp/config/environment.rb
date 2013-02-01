# Load the rails application
require File.expand_path('../application', __FILE__)

# Needed for the local case for asset precompilation.
# Please comment these two lines out when deploying on NetWeaver Cloud
# require 'jdbc/sqlite3'
# Jdbc::SQLite3.load_driver(:require) if Jdbc::SQLite3.respond_to?(:load_driver)

# Initialize the rails application
Photoapp::Application.initialize!
