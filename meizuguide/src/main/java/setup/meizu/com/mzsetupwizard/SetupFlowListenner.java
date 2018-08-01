package setup.meizu.com.mzsetupwizard;

/**
 * Created by zhengsiyuan on 2017/7/19.
 */

public interface SetupFlowListenner {
    void onNext(String name);

    void onBack(String name);
}
