package Arzanese.TrovaCasa.immobili.immagini_immobili;

import Arzanese.TrovaCasa.cloudinary.CloudinaryService;
import Arzanese.TrovaCasa.immobili.Immobile;
import Arzanese.TrovaCasa.immobili.ImmobileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImmagineImmobileService {

    @Autowired
    private ImmagineImmobileRepository immagineImmobileRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ImmobileRepository immobileRepository;


    @Transactional
    public List<ImmagineImmobile> uploadImmagini(Long immobileId, List<MultipartFile> files, boolean copertina) throws IOException {
        Immobile immobile = immobileRepository.findById(immobileId)
                .orElseThrow(() -> new IllegalArgumentException("Immobile non trovato con ID: " + immobileId));

        List<ImmagineImmobile> immaginiSalvate = new ArrayList<>();

        for (MultipartFile file : files) {
            // Carica immagine su Cloudinary
            String imageUrl = cloudinaryService.uploadImage(file.getBytes(), file.getOriginalFilename());

            // Crea e salva un'istanza di ImmagineImmobile
            ImmagineImmobile immagineImmobile = new ImmagineImmobile();
            immagineImmobile.setUrlImmagine(imageUrl);
            immagineImmobile.setCopertina(copertina ? "true" : "false");
            immagineImmobile.setImmobile(immobile);

            immaginiSalvate.add(immagineImmobileRepository.save(immagineImmobile));
            copertina = false; // Solo la prima immagine può essere copertina, il resto no
        }

        return immaginiSalvate;
    }

}
