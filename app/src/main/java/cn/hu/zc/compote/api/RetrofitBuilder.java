package cn.hu.zc.compote.api;

import cn.hu.zc.compote.api.idata.ApiIdataUrl;
import cn.hu.zc.compote.api.idata.IdataService;
import cn.hu.zc.compote.api.juhe.ApiJuheUrl;
import cn.hu.zc.compote.api.juhe.JuheService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    private static IdataService idataService;
    private static JuheService juheService;

    /**
     * @return idata数据
     */
    public static IdataService getIdata() {
        if (idataService == null) {
            synchronized (RetrofitBuilder.class) {
                if (idataService == null) {
                    Retrofit retrofitIdata = new Retrofit.Builder()
                            .baseUrl(ApiIdataUrl.BASE)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create()).build();
                    idataService = retrofitIdata.create(IdataService.class);
                }
            }
        }
        return idataService;
    }

    /**
     * @return 聚合数据
     */
    public static JuheService getJuhe() {
        if (juheService == null) {
            synchronized (RetrofitBuilder.class) {
                if (juheService == null) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(ApiJuheUrl.BASE)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create()).build();
                    juheService = retrofit.create(JuheService.class);
                }
            }
        }
        return juheService;
    }

}
