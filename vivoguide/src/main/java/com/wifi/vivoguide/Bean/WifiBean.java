package com.wifi.vivoguide.Bean;

import com.wifi.vivoguide.Business.WifiHelper.PskType;

public class WifiBean implements Comparable<WifiBean> {

	public static final int TYPE_WIFICONFIGURATION = 0;
	public static final int TYPE_SCANRESULT = 1;

	private String mSsid;
	private String mPasswd;
	private boolean mIsStatic;
	private PskType mPskType;
	private String mIp;
	private String mIdentity;
	/**
	 * 网络前缀长度 4.x使用
	 */
	private int mNetworkPrefix;
	/**
	 * 子网掩码2.x使用
	 */
//	private String netmask;
	private String mGateway;
	private String mDns1;
	private String mDns2;
	private int mType;

	// private ScanResult result;
	private int mLevel = -100;
	private String mCapabilities;

	// private WifiConfiguration configuration;
	private int mNetworkId = -1;

	private boolean mIsCurrentUsed = false;

	@Override
	public int compareTo(WifiBean another) {
		if (this.mIsCurrentUsed) {
			return -1;
		}
		if (another.mIsCurrentUsed) {
			return 1;
		}

		if (this.mType > another.mType) {
			return -1;
		} else if (this.mType < another.mType) {
			return 1;
		} else {
			if (this.mNetworkId > another.mNetworkId) {
				return -1;
			} else if (this.mNetworkId < another.mNetworkId) {
				return 1;
			} else {
				if (this.mLevel > another.mLevel) {
					return -1;
				} else if (this.mLevel < another.mLevel) {
					return 1;
				} else {
					return this.mSsid.compareTo(another.mSsid);
				}
			}
		}
	}

	@Override
	public String toString() {
		return "WifiBean [ssid=" + mSsid + ", passwd=" + mPasswd + ", isStatic=" + mIsStatic + ", pskType=" + mPskType + ", ip=" + mIp + ", gateway=" + mGateway + ", dns1=" + mDns1 + ", dns2=" + mDns2 + ", type=" + mType + ", level=" + mLevel + ", capabilities=" + mCapabilities + ", networkId=" + mNetworkId + ", isCurrentUsed=" + mIsCurrentUsed + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass()) {
			return false;
		}
		WifiBean other = (WifiBean) obj;
		if (mSsid == null) {
			if (other.mSsid != null) {
				return false;
			}
		} else if (!mSsid.equals(other.mSsid)) {
			return false;
		} else if (mSsid.equals(other.mSsid)) {
			if (mLevel != other.mLevel) {
				return false;
			}
		}
		return true;
	}

	public String getCapabilities() {
		return mCapabilities;
	}

	public String getDns1() {
		return mDns1;
	}

	public String getDns2() {
		return mDns2;
	}

	public String getGateway() {
		return mGateway;
	}

	public String getIp() {
		return mIp;
	}

	public int getLevel() {
		return mLevel;
	}

	public int getNetworkId() {
		return mNetworkId;
	}

	public String getPasswd() {
		return mPasswd;
	}

	public PskType getPskType() {
		return mPskType;
	}

	public String getSsid() {
		return mSsid;
	}

	public int getType() {
		return mType;
	}

	public String getIdentity() {
		return mIdentity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mSsid == null) ? 0 : mSsid.hashCode());
		return result;
	}

	public boolean isCurrentUsed() {
		return mIsCurrentUsed;
	}

	public boolean isStatic() {
		return mIsStatic;
	}

	public void setCapabilities(String capabilities) {
		this.mCapabilities = capabilities;
	}

	public void setCurrentUsed(boolean isCurrentUsed) {
		this.mIsCurrentUsed = isCurrentUsed;
	}

	public void setDns1(String dns1) {
		this.mDns1 = dns1;
	}

	public void setDns2(String dns2) {
		this.mDns2 = dns2;
	}

	public void setGateway(String gateway) {
		this.mGateway = gateway;
	}

	public void setIp(String ip) {
		this.mIp = ip;
	}

	public void setLevel(int level) {
		this.mLevel = level;
	}

	public void setNetworkId(int networkId) {
		this.mNetworkId = networkId;
	}

	public void setPasswd(String passwd) {
		this.mPasswd = passwd;
	}

	public void setPskType(PskType pskType) {
		this.mPskType = pskType;
	}

	public void setIdentity(String identity) {
		this.mIdentity = identity;
	}
	public void setSsid(String ssid) {
		this.mSsid = ssid;
	}

	public void setStatic(boolean isStatic) {
		this.mIsStatic = isStatic;
	}

	public void setType(int type) {
		this.mType = type;
	}

	public int getNetworkPrefix() {
		return mNetworkPrefix;
	}

	public void setNetworkPrefix(int networkPrefix) {
		this.mNetworkPrefix = networkPrefix;
	}

}
