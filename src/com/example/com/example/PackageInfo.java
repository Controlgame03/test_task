package com.example;

public class PackageInfo {
    private String name;
    private String versionRelease;
    private String arch;

    public PackageInfo(String name, String versionRelease, String arch) {
        this.name = name;
        this.versionRelease = versionRelease;
        this.arch = arch;
    }

    public String getName() {
        return name;
    }

    public String getVersionRelease() {
        return versionRelease;
    }

    public String getArch() {
        return arch;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PackageInfo that = (PackageInfo) obj;
        return name.equals(that.name) && arch.equals(that.arch);
    }

    @Override
    public int hashCode() {
        return name.hashCode() + arch.hashCode();
    }

    @Override
    public String toString() {
        return "PackageInfo{name='" + name + "', versionRelease='" + versionRelease + "', arch='" + arch + "'}";
    }
}

