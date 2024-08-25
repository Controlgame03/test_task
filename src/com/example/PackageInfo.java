package com.example;

public class PackageInfo {
    private String name;
    private int epoch;
    private String version;
    private String release;
    private String disttag;
    private int buildtime;
    private String arch;

    public PackageInfo(String name, int epoch, String version, String release, String disttag, int buildtime, String arch) {
        this.name = name;
        this.epoch = epoch;
        this.version = version;
        this.release = release;
        this.disttag = disttag;
        this.buildtime = buildtime;
        this.arch = arch;
    }

    public String getName() {
        return name;
    }

    public int getEpoch() {
        return epoch;
    }

    public String getVersion() {
        return version;
    }

    public String getRelease() {
        return release;
    }

    public String getDisttag() {
        return disttag;
    }

    public int getBuildtime() {
        return buildtime;
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
        return "PackageInfo{name='" + name + "', epoch='" + epoch + "', version='" + version + "', release='" + release + "', disttag='" + disttag + ", buildtime=" + buildtime + "', arch='" + arch + "'}";
    }
}

