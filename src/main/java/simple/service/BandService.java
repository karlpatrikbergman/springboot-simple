package simple.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import simple.repository.BandEntity;
import simple.repository.BandEntityTO;
import simple.repository.BandRepository;

import java.util.List;

@Service
public class BandService {

    private BandRepository bandRepository;

    @Autowired
    public BandService(BandRepository bandRepository) {
        this.bandRepository = bandRepository;
    }

    public BandEntity createBand(BandEntityTO bandEntityTO) {
        return bandRepository.createBand(bandEntityTO);
    }

    public BandEntity getBand(int id) {
        return bandRepository.getBand(id);
    }

    public BandEntity updateBand(int id, BandEntityTO bandEntityTO) {
        return bandRepository.updateBand(id, bandEntityTO);
    }

    public void deleteBand(int id) {
        bandRepository.deleteBand(id);
    }

    public List<BandEntity> getBands() {
        return bandRepository.getBands();
    }

}
