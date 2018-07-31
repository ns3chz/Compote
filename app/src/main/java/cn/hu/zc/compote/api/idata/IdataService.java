package cn.hu.zc.compote.api.idata;

import cn.hu.zc.compote.api.common.model.news.ModelNews;
import cn.hu.zc.compote.api.idata.model.ModelIdataNetDataList;
import cn.hu.zc.compote.api.idata.model.news.ModelCCTVnews;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IdataService {
    /**
     * @param pageToken 翻页值
     * @param beginDate 只有按关键词搜索时该参数生效
     * @param endDate   只有按关键词搜索时该参数生效
     * @param city      只有按关键词搜索时该参数生效
     * @param catid     catid
     * @param kw        关键词，不填为不限
     */
    @GET(ApiIdataUrl.NEWS_CCTV)
    Observable<ModelIdataNetDataList<ModelCCTVnews, ModelNews>> getCCTVnews(@Query("pageToken") String pageToken, @Query("beginDate") String beginDate,
                                                                            @Query("endDate") String endDate, @Query("city") String city,
                                                                            @Query("catid") String catid, @Query("kw") String kw);
}
