package com.example;

public class PackageInfo {
    private String name;
    private String version;
    private String release;
    private String arch;

    public PackageInfo(String name, String version, String release, String arch) {
        this.name = name;
        this.version = version;
        this.release = release;
        this.arch = arch;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }
    
    public String getRelease() {
        return release;
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
        return "PackageInfo{name='" + name + "', version='" + version + "', release='" + release + "', arch='" + arch + "'}";
    }
}

