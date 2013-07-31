(ns nuget 
  (:import [NuGet PackageRepositoryFactory PackageManager PackageRepositoryExtensions]))

(def package-path "packages/")
(def nuget-uri-v2 "https://nuget.org/api/v2/")
(def repo (.CreateRepository PackageRepositoryFactory/Default nuget-uri-v2))
(def package-manager (PackageManager. repo package-path))

(defn find-package [package-name]
   (PackageRepositoryExtensions/FindPackage repo package-name))

; (defn search [package-name allow-prerelease]
;    (PackageRepositoryExtensions/Search repo package-name allow-prerelease))

(defn install [package-name]
  (println (str "installing " package-name))
  (.InstallPackage package-manager package-name))

(defn uninstall [package-name]
  (println (str "uninstalling " package-name))
  (.UninstallPackage package-manager package-name nil true true))
; var repo =
;             PackageRepositoryFactory.Default.CreateRepository(
;                 new PackageSource("http://nuget.local/nuget/Packages", "Default"));
;
; var repository = new AggregateRepository(
    ; new [] {
    ;     PackageRepositoryFactory.Default.CreateRepository(
