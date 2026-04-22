package vn.edu.nlu.fit.mythuatshop.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import vn.edu.nlu.fit.mythuatshop.Dao.LogDao;
import vn.edu.nlu.fit.mythuatshop.Model.Log;

import java.sql.Timestamp;

import static java.lang.System.currentTimeMillis;


public class LogService {
    private final LogDao logDao = new  LogDao();
    private final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    public void log(String label, int userId, String location, Object beforeObj, Object afterObj) {
        String beforeDta = beforeObj == null ? null : gson.toJson(beforeObj);
        String  afterDta = afterObj == null ? null : gson.toJson(afterObj);

        Log log = new Log(label, userId, new Timestamp(currentTimeMillis()), location, beforeDta, afterDta);
        logDao.insert(log);
    }

}
