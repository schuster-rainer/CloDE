;(assembly-load-from "./libs/Mono.Cecil/lib/net40/Mono.Cecil.dll")
; (assembly-load-with-partial-name "mscorlib")

(ns monocecil
  (:import [Mono.Cecil AssemblyDefinition ModuleDefinition])
  (:import [ICSharpCode.Decompiler DecompilerContext PlainTextOutput]) 
  (:import [ICSharpCode.Decompiler.Ast AstBuilder]) 
  (:import [System.IO StringWriter]) 
  )

; (def filename "./libs/Mono.Cecil/lib/net40/Mono.Cecil.dll")
; (def assembly (AssemblyDefinition/ReadAssembly filename))

; (def decompiler (AstBuilder. (DecompilerContext. (.MainModule assembly))))

; (. decompiler AddAssembly assembly false)
; (def output (StringWriter.))

; (. decompiler GenerateCode (PlainTextOutput. output))

; (def module (ModuleDefinition/ReadModule filename))
; (doseq [t (. module Types)]
;   (print t))



(defn load-assembly [filename]
  (let [assembly (AssemblyDefinition/ReadAssembly filename)
        decompiler (AstBuilder. (DecompilerContext. (.MainModule assembly)))
        output (StringWriter.)]
    (. decompiler AddAssembly assembly false)
    (. decompiler GenerateCode (PlainTextOutput. output))
    (print output)))

(defn assembly-path-from-type [t]
  (-> t .GetType .Assembly .CodeBase Uri. .LocalPath))

(defn module-from-type [t]
  (-> t assembly-path-from-type ModuleDefinition/ReadModule))

(defn create-decompiler [module]
  (-> module DecompilerContext. AstBuilder.))

(defn get-types [t module]
   (filter (fn [typedef] (= (. typedef FullName)
                         (. t FullName)))
        (.Types module)))

(defn generate-plaintext [asm]
  (let [output (StringWriter.)]
    (.GenerateCode (:decompiler asm) (PlainTextOutput. output))
    output))

(defn add-type [asm]
  (.AddType (:decompiler asm) (first (:typedefs asm))))

#_(defn dasm [t asm-ref]
  (let [module (module-from-type t)
        decompiler (create-decompiler module)
        typedefs (get-types t module)]
    (ref-set asm-ref { :type t
                  :typedefs typedefs
                  :module module
                  :decompiler decompiler})))


(defstruct asm :type :typedefs :module :decompiler)

(defn dasm [t]
  (let [module (module-from-type t)
        decompiler (create-decompiler module)
        typedefs (get-types t module)]
    (struct-map asm :type t
                  :typedefs typedefs
                  :module module
                  :decompiler decompiler)))
