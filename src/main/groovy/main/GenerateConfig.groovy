package main

/**
 * Created by romulofc on 28/05/16.
 */
class GenerateConfig {

    private static String BASE_FILE = new File(this.getClass().getResource('/test/teste_stress_plugin.jmx').getFile()).text

    def static generateFile(int counter, int count, int start, int inc){
        def engine = new groovy.text.SimpleTemplateEngine()
        def template = engine.createTemplate(BASE_FILE).make([counter:counter,count:count,start:start,inc:inc])
        return template.toString()
    }

    def static restartVM(){
        "VBoxManage controlvm guardiao-stress acpipowerbutton".execute().waitFor()
        def state = ""
        while(!state.contains("powered off")){
            state = "VBoxManage showvminfo guardiao-stress".execute().text
        }

        "VBoxManage startvm guardiao-stress --type headless".execute().waitFor()
        def returnCode = 0
        while(returnCode != 200){
            try{
                URL url = new URL("http://127.0.0.1:8080/")
                HttpURLConnection http = (HttpURLConnection)url.openConnection()
                returnCode = http.getResponseCode()
            }catch (e){}

        }

    }
}
