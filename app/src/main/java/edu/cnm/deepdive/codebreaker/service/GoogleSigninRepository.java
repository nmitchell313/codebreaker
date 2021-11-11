package edu.cnm.deepdive.codebreaker.service;

import android.app.Application;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import edu.cnm.deepdive.codebreaker.BuildConfig;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.schedulers.Schedulers;

public class GoogleSigninRepository {

  private static final String BEARER_TOKEN_FORMAT = "Bearer %s";

  private static Application context;

  private final GoogleSignInClient client;

  private GoogleSignInAccount account;

  private GoogleSigninRepository() {
    GoogleSignInOptions options = new GoogleSignInOptions.Builder()
        .requestEmail()
        .requestId()
        .requestProfile()
//        .requestIdToken(BuildConfig.CLIENT_ID)
        .build();
    client = GoogleSignIn.getClient(context, options);
  }

  public static void setContext(Application context) {
    GoogleSigninRepository.context = context;
  }

  public static GoogleSigninRepository getInstance() {
    return InstanceHolder.INSTANCE;
  }

  public Single<GoogleSignInAccount> refresh() {
    return Single
        .create((SingleOnSubscribe<GoogleSignInAccount>) (emitter) ->
            client
                .silentSignIn()
                .addOnSuccessListener(this::setAccount)
                .addOnSuccessListener(emitter::onSuccess)
                .addOnFailureListener(emitter::onError)
        )
        .observeOn(Schedulers.io());
  }

  public void setAccount(GoogleSignInAccount account) {
    this.account = account;
  }

  private String getBearerToken(GoogleSignInAccount account) {
    return String.format(BEARER_TOKEN_FORMAT, account.getIdToken());
  }

  private static class InstanceHolder {

    private static final GoogleSigninRepository INSTANCE = new GoogleSigninRepository();
  }
}
