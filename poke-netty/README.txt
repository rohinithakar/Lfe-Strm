This project provides you with a basic framework to build your 
project 1 nodes. This is not a complete project nor a fully 
tested and robust solution. It is your responsibility to 
extend and validate the project.

Communication:

A common approach for distributed systems is to separate 
domain requests from management (internal) requests. The 
motivation is often driven by a need to provide the highest 
potential to prioritize and control resources. This project 
provides a design for two networks. The connections are:

   1) public for satisfying data requests
   2) private (mgmt) for internal synchronization and 
      coordination


Storage:

A storage framework is provided to show you a way (others exists) 
to decouple domain logic from backend persistence.


For PostGis
sudo apt-get install libxml2-dev libjson0-dev libproj0 postgresql-server-dev-9.2 libgeos-dev proj
///Compile post-gis-2.0.3
./configure --without-raster

http://trac.osgeo.org/postgis/wiki/UsersWikiPostGIS20Ubuntu1204