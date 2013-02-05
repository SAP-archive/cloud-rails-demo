require 'java'

java_import javax.naming.InitialContext
java_import org.apache.http.client.methods.HttpGet
java_import org.apache.http.util.EntityUtils


class WeatherController < ApplicationController

  def index

    ctx = InitialContext.new
    destination = ctx.lookup("java:comp/env/weather")
    httpClient = destination.createHttpClient
    httpGet = HttpGet.new("forecastrss?p=BUXX0005&u=c")

    # Execute the request
    httpResponse = httpClient.execute(httpGet)

    # Process the response
    entity = httpResponse.getEntity
    @respToString = EntityUtils.toString(entity)

  end

end