class Photo < ActiveRecord::Base
  belongs_to :album  
  attr_accessible :album_id, :picture_name, :title
  validates :title, presence: true
  validates :picture_name, presence: true  
  
  
  def delete_file
    return unless picture_name
	File.delete( internal_path ) rescue nil
  end  
  
  # Used for displaying image in UI
  def picture_web_path
    File.join("/uploaded", picture_name)
  end  
  
  private
  def internal_path
    return nil if picture_name.nil?
	directory = "public/uploaded"
	return File.join(directory, picture_name)
  end  
  
end
