require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "react_native_image_ocr"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.homepage     = package["homepage"]
  s.license      = package["license"]
  s.authors      = package["author"]

  s.platforms    = { :ios => "13.0" }
  s.source       = { :git => "https://github.com/gzqyl/react-native-image-ocr", :tag => "#{s.version}" }

  s.source_files = "ios/**/*.{h,m,mm,swift}"

  s.dependency "React-Core"
  s.dependency "GoogleMLKit/TextRecognition", "3.2.0"
  s.dependency "GoogleMLKit/TextRecognitionChinese", "3.2.0"
  s.dependency "GoogleMLKit/TextRecognitionJapanese", "3.2.0"
  s.dependency "GoogleMLKit/TextRecognitionKorean", "3.2.0"
end