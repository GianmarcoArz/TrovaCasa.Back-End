package Arzanese.TrovaCasa.immobili.immagini_immobili;

import Arzanese.TrovaCasa.cloudinary.CloudinaryService;
import Arzanese.TrovaCasa.immobili.Immobile;
import Arzanese.TrovaCasa.immobili.ImmobileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImmagineImmobileService {

    @Autowired
    private ImmagineImmobileRepository immagineImmobileRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ImmobileRepository immobileRepository;


    @Transactional
    public ImmagineImmobile uploadImmagine(Long immobileId, MultipartFile file, boolean copertina) throws IOException {
        Immobile immobile = immobileRepository.findById(immobileId)
                .orElseThrow(() -> new IllegalArgumentException("Immobile non trovato con ID: " + immobileId));

        // Carica immagine su Cloudinary
        String imageUrl = cloudinaryService.uploadImage(file.getBytes(), file.getOriginalFilename());

        // Crea e salva un'istanza di ImmagineImmobile
        ImmagineImmobile immagineImmobile = new ImmagineImmobile();
        immagineImmobile.setUrlImmagine(imageUrl);
        immagineImmobile.setCopertina(copertina ? "true" : "false");
        immagineImmobile.setImmobile(immobile);

        return immagineImmobileRepository.save(immagineImmobile);
    }

}
