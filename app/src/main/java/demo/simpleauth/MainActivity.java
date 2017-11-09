package demo.simpleauth;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.jaychang.sa.AuthCallback;
import com.jaychang.sa.SimpleAuth;
import com.jaychang.sa.SocialUser;
import com.jaychang.sa.TwitterUser;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

  public static final String TWITTER = "TWITTER";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
  }





  @OnClick(R.id.connectTwitterButton)
  void connectTwitter() {
    SimpleAuth.getInstance().connectTwitter(new AuthCallback() {
      @Override
      public void onSuccess(SocialUser socialUser) {
        if (socialUser instanceof TwitterUser) {
          TwitterUser twitterUser = (TwitterUser) socialUser;
          Log.e("test", "onTwitSuccess: " + twitterUser.secret );
        }
        ProfileActivity.start(MainActivity.this, TWITTER, socialUser);
      }

      @Override
      public void onError(Throwable error) {
        toast(error.getMessage());
      }

      @Override
      public void onCancel() {
        toast("Canceled");
      }
    });
  }



  private void toast(String msg) {
    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
  }

}
