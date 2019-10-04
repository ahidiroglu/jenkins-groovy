import groovy.json.JsonSlurper

//
def findDockerImages(branchName) {
  def versionList = []
  def token       = ""
  def myJsonreader = new JsonSlurper()
  def nexusData = myJsonreader.parse(new URL("https://nexus.fuchicorp.com/service/rest/v1/components?repository=webplatform"))
  while (true) {
    if (nexusData.continuationToken) {
      nexusData.items.each {
        if (it.name.contains(branchName)) {
           versionList.add('docker.fuchicorp.com/'  +it.name + ':' + it.version)
         }
        }
      token = nexusData.continuationToken
      nexusData = myJsonreader.parse(new URL("https://nexus.fuchicorp.com/service/rest/v1/components?repository=webplatform&continuationToken=${token}"))
      nexusData.items.each {
        if (it.name.contains(branchName)) {
           versionList.add('docker.fuchicorp.com/'  + it.name + ':' + it.version)
         }
        }
    }
    if (nexusData.continuationToken == null ){
      break
    }
  }
  return versionList
}

println(findDockerImages('dev'))
