 
# This initializer runs the equivalent of rake db:migrate on application start
# It relies on having all the 'db' folder contents packaged with the application's WAR

p "============================= Executing application migrations ... ============================= "
migrations = Rails.root.join('db','migrate')
ActiveRecord::Migrator.migrate(migrations)
p "============================= Application migrations are executed. ============================= "