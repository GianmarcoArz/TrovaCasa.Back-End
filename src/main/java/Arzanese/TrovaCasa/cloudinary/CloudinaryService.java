package Arzanese.TrovaCasa.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadImage(byte[] imageData, String fileName) throws IOException {
        Map<String, Object> uploadParams = ObjectUtils.asMap(
                "public_id", fileName,
                "overwrite", true
        );
        Map uploadResult = cloudinary.uploader().upload(imageData, uploadParams);
        return (String) uploadResult.get("secure_url");
    }

}
