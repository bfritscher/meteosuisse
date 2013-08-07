import groovy.json.JsonSlurper
import groovy.json.JsonBuilder

def base = 'http://app-prod-ws.meteoswiss.ch/'
def basePic = 'http://app-prod-static.meteoswiss.ch/'

//TODO cleanup remove png * zip

def url = base + 'precipitationFileList'
def json = new JsonSlurper().parseText(url.toURL().text)
def pics = []
json.each{
    it.files.each{ file ->
        def localfile = new FileOutputStream(file)
        def out = new BufferedOutputStream(localfile)
        out << new URL(basePic + file).openStream()
        out.close()
        def zipFile = new java.util.zip.ZipFile(new File(file))
        zipFile.entries().each {
			pics << it.name
			def pic = new File(it.name)
			pic.newOutputStream() << zipFile.getInputStream(it)
        }
    }
}
def outFile = new File('data.js')
outFile.write "var pics = ${groovy.json.JsonOutput.toJson(pics.sort())};"