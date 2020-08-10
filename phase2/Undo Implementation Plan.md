#UNDO
####By Warren Zhu

###What it means
According to the Phase 2 instructions, we are supposed to be able to "[g]ive the admin user the ability to undo every action taken by regular users that can reasonably be undone."

Before implementing this feature, we must carefully decide on what this means (i.e., what actions can be undone). Below is a list outlining what is undoable, what isn't undoable, and why/how.

####Undoable:
#####1)A Trade



#####2)Accepting or Declining 



#####3)Uncancelling A Meeting



#####4)

####Not Undoable:
#####1) Sending messages

Once a message is sent, users should not be able to retract what they've said. We want people to be careful with their words, and to think carefully before a message is composed and sent.

#####2) Account Creation

#####3) Accepting A Trade

Administrators cannot undo the act of a user accepting a trade

#####4) Accepting or Confirming A Meeting





##Implementation

Undoing isn't really about how entities interact--rather, it's about what each user can do.


