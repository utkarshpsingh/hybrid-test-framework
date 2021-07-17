'''
Created on Jan 13, 2020

@author: AOhri
'''
from __future__ import with_statement

# Read in the original and new file          
orig = open('/Users/AOhri/Downloads/AtuomationFramework/Results/Test1.csv','r')
new = open('/Users/AOhri/Downloads/AtuomationFramework/Results/Test2.csv','r')
#in new but not in orig
bigb = set(new) - set(orig)
# To see results in console if desired
print(bigb)
# Write to output file    
with open('/Users/AOhri/Downloads/AtuomationFramework/Results/difference.csv', 'w') as file_out:
    for line in bigb:
        file_out.write(line)
#close the files  
orig.close()    
new.close()    
file_out.close()


