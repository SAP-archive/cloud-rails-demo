# This script compiles application's Java code against your local SAP NetWeaver Cloud SDK

netweaver_cloud_sdk_location = <file_system_path_to_extracted_NWCloud_SDK>
CLASSPATH = Dir["#{netweaver_cloud_sdk_location}/api/*.jar"].join(File::PATH_SEPARATOR)
%x(javac -cp #{CLASSPATH} #{Dir['src/**/*.java'].join(' ')} -d classes)