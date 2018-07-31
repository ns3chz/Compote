package cn.hu.zc.compote.api.juhe;

import cn.hu.zc.compote.api.common.model.news.ModelNews;
import cn.hu.zc.compote.api.juhe.model.ModelBaseJuheNetDataList;
import cn.hu.zc.compote.api.juhe.model.news.ModelToutiao;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JuheService {

    @GET(ApiJuheUrl.TOUTIAO)
    Observable<ModelBaseJuheNetDataList<ModelToutiao, ModelNews>> getTouTiao(@Query("type") String type);
}
