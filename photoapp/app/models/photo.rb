require 'java'
require 'cmis_client.jar'
java_import 'cmis.client.CmisClient'

class Photo < ActiveRecord::Base
  belongs_to :album  
  attr_accessible :album_id, :picture_name, :title
  validates :title, presence: true
  validates :picture_name, presence: true  
  
  after_destroy :delete_picture
  
  @@repo_id = CmisClient.getRepositoryID
  
  def delete_picture
    return unless picture_name
	CmisClient.deletePhoto(picture_name)
  end
  
  # Used for displaying image in UI
  def picture_web_path
    id = CmisClient.getObjectId(picture_name)	
    if id != nil
      url = "/cmis/atom/" + @@repo_id + "/content/" + picture_name + "?id=" + id
    else
      url = "no-image"
    end
    return url
  end 
  
end
