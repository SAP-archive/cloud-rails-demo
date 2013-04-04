# This script compiles application's Java code against your local SAP HANA Cloud SDK

hana_cloud_sdk_location = <file_system_path_to_extracted_HANACloud_SDK>
CLASSPATH = Dir["#{hana_cloud_sdk_location}/api/*.jar"].join(File::PATH_SEPARATOR)
%x(javac -cp #{CLASSPATH} #{Dir['src/**/*.java'].join(' ')} -d classes)