import jenkins.model.Jenkins
import hudson.model.User

def allUsers = User.getAll()
def adminList = []
def authStrategy = Jenkins.instance.getAuthorizationStrategy()

def existingGroups = authStrategy.getGroups()
