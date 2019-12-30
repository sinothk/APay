# APay
APay



# Step 1. Add the JitPack repository to your build file
  Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}

# Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.sinothk:APay:1.x.1230'
	}
# WX

1. APay.initWxPay(this, Constants.APP_ID);
2. APay.checkWxEnable();
3. APay.sendWxReq(new PayReq());
