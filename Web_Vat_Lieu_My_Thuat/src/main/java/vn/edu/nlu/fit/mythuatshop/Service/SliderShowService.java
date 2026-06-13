package vn.edu.nlu.fit.mythuatshop.Service;

import vn.edu.nlu.fit.mythuatshop.Dao.SliderShowDao;
import vn.edu.nlu.fit.mythuatshop.Model.SliderShow;

import java.util.List;
import java.util.Optional;

public class SliderShowService {
    private final SliderShowDao sliderShowDao;
    private static List<SliderShow> sliderCache = null;
    private static long sliderCacheTime = 0;
    private static final long CACHE_TIME = 5 * 60 * 1000;

    public SliderShowService() {
        this.sliderShowDao = new SliderShowDao();
    }


    public List<SliderShow> getSliderShow() {
        return getSliderShowByCache();
    }

    public List<SliderShow> getSliderShowByCache() {
        long now = System.currentTimeMillis();
        if (sliderCache != null && now - sliderCacheTime < CACHE_TIME) {
            return sliderCache;
        }
        sliderCache = sliderShowDao.getSliderShowAll();
        sliderCacheTime = now;
        return sliderCache;
    }
    public void clearSliderCache() {
        sliderCache = null;
        sliderCacheTime = 0;
    }
    public int countByKeyword(String keyword) {
        return sliderShowDao.countByKeyword(keyword);
    }

    public List<SliderShow> findPageByKeyword(String keyword, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return sliderShowDao.findPageByKeyword(keyword, offset, pageSize);
    }

    public Optional<SliderShow> findById(int id) {
        return sliderShowDao.findById(id);
    }


    public boolean existsIndexOrder(int indexOrder, Integer excludeId) {
        return sliderShowDao.existsIndexOrder(indexOrder, excludeId);
    }


    public int create(SliderShow s) {
        int id = sliderShowDao.insert(s);
        clearSliderCache();
        return id;
    }

    public boolean update(SliderShow s) {
        boolean result = sliderShowDao.update(s)>0;
        clearSliderCache();
        return result;
    }

    public boolean delete(int id) {
        boolean result = sliderShowDao.delete(id)>0;
        clearSliderCache();
        return result;
    }


    public boolean toggleStatus(int id, int currentStatus) {
        int newStatus = (currentStatus == 1) ? 0 : 1;
        boolean result = sliderShowDao.updateStatus(id, newStatus)>0;
        clearSliderCache();
        return result;
    }
}
