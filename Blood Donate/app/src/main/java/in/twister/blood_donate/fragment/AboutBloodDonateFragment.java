package in.twister.blood_donate.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import in.twister.blood_donate.Bean.ArticleBean;
import in.twister.blood_donate.Connection.ConnectionConfig;
import in.twister.blood_donate.Connection.ConnectionMethod;
import in.twister.blood_donate.R;

public class AboutBloodDonateFragment extends Fragment {
    TextView title;
    WebView article;

    ArticleBean bean;

    public AboutBloodDonateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_blood_donate, container, false);

        title = view.findViewById(R.id.title);
        article = view.findViewById(R.id.article);

        ConnectionMethod method = ConnectionConfig.getApiClient().create(ConnectionMethod.class);
        Call<ArticleBean> call = method.getArticle();

        call.enqueue(new Callback<ArticleBean>() {
            @Override
            public void onResponse(@NonNull Call<ArticleBean> call, @NonNull Response<ArticleBean> response) {
                bean = response.body();
                if (bean != null && !bean.getError()) {
                    title.setText(bean.getTitle());
                    String articleText = "<HTML><HEAD><Style type='text/css'>" +
                            "p{text-align:justify; text-indent:19px; font-size:20px}" +
                            "</Style></HEAD><BODY>" +
                            bean.getArticle() +
                            "</Body></HTML>";
                    article.loadData(String.format(" %s ", articleText), "text/html", "utf-8");
                } else {
                    title.setText(getResources().getStringArray(R.array.default_Article)[0]);
                    String articleText = "<HTML><HEAD><Style type='text/css'>" +
                            "p{text-align:justify; text-indent:19px; font-size:20px}" +
                            "</Style></HEAD><BODY>" +
                            getResources().getStringArray(R.array.default_Article)[1] +
                            "</Body></HTML>";
                    article.loadData(String.format(" %s ", articleText), "text/html", "utf-8");
                }
            }
            @Override
            public void onFailure(@NonNull Call<ArticleBean> call, @NonNull Throwable t) {
                title.setText(getResources().getStringArray(R.array.default_Article)[0]);
                String articleText = "<HTML><HEAD><Style type='text/css'>" +
                        "p{text-align:justify; text-indent:19px; font-size:20px}" +
                        "</Style></HEAD><BODY>" +
                        getResources().getStringArray(R.array.default_Article)[1] +
                        "</Body></HTML>";
                article.loadData(String.format(" %s ", articleText), "text/html", "utf-8");
            }
        });
        return view;
    }
}
