#include <jni.h>
#include <vector>
#include <string>
#include <sstream>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_carpartsshop_ui_cart_CartFragment_stringFromJNI(
                JNIEnv *env,
                jobject,
                jintArray prices) {
    jsize length = env->GetArrayLength(prices);
    jint *priceArray = env->GetIntArrayElements(prices, nullptr);

    double totalPrice = 0.0;
    for (jsize i = 0; i < length; ++i) {
        totalPrice += priceArray[i];
    }

    env->ReleaseIntArrayElements(prices, priceArray, 0);

    std::ostringstream strs;
    strs << totalPrice;
    std::string totalPriceStr = strs.str();

    return env->NewStringUTF(totalPriceStr.c_str());
}