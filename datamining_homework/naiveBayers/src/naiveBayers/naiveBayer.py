'''
Created on 2016.3.25

@author: summer
'''

from numpy import *
#from _ast import Num

attributeList=['age','income','student','credit_rating','buys_computer'] 

def loadDataSet():
    data=[       ['<30', 'High', 'No', 'Fair','No'],
                 ['<30', 'High', 'No', 'Excellent','No'],
                 ['30-40', 'High', 'No', 'Fair','Yes'],
                 ['>40', 'Medium', 'No', 'Fair','Yes'],
                 ['>40', 'Low', 'Yes', 'Fair','Yes'],
                 ['>40', 'Low', 'Yes', 'Excellent','No'],
                 ['30-40', 'Low', 'Yes', 'Excellent','Yes'],
                 ['<30', 'Medium', 'No', 'Fair','No'],
                 ['<30', 'Low', 'Yes', 'Fair','Yes'],
                 ['>40', 'Medium', 'Yes', 'Fair','Yes'],
                 ['<30', 'Medium', 'Yes', 'Excellent','Yes'],
                 ['30-40', 'Medium', 'No', 'Excellent','Yes'],
                 ['30-40', 'High', 'Yes', 'Fair','Yes'],
                 ['>30', 'Medium', 'No', 'Excellent','No'] ]
    classVec = ['No', 'No', 'Yes', 'Yes', 'Yes', 'No', 'Yes', 'No', 'Yes', 'Yes', 'Yes', 'Yes', 'Yes', 'No']         
    return data,classVec

def subData(dataset,attributeName,attribute):
    subset=[]
    index=attributeList.index(attributeName)
    for data in dataset:
        if attribute == data[index]:
            subset.append(data)
    return subset

def count(dataset,attributeName,attribute):
    num=0
    attributeList=['age','income','student','credit_rating']
    index=attributeList.index(attributeName)
    for data in dataset:
        if attribute == data[index]:
            num=num+1
    return num

def numof(dataset):
    return len(dataset)

def printdata(dataset):
    for data in dataset :
        print data
        
def typeof(category,dataset):
    type=[]
    index=attributeList.index(category)    
    for data in dataset:
        if data[index] in type:
            continue
        else: type.append(data[index])
    return type

def devidedata(data,category): 
    devide_data=[]     
    type=typeof(category,data)       
    for i in range(0,len(type)):
        devide_data.append(subData(data,category,type[i]))    
           
    '''
    print('the category is {0}').format(category)    
    for data in devide_data: 
        print('this is the {0} th subset of whole data').format(devide_data.index(data))
        printdata(data)
    '''        
    return devide_data

def computeP(data,subset,aim):
    P_subset=numof(subset)/float16(numof(data))
    for i in range(0,len(attributeList)-1):
        P_subset *= count(subset,attributeList[i],aim[i]) /float16(numof(subset))     
    return P_subset     
        
def decide(rawdata,devide_data,category,aim):
    result=[]
    type=typeof(category,rawdata) 
    for data in devide_data:
        P=computeP(rawdata, data, aim)
        result.append(P)
        print('the probability of "{0}" is {1}').format(type[devide_data.index(data)],P)
    max_index=result.index(max(result))
    decision=typeof(category, rawdata)
    return decision[max_index]
    
if __name__=='__main__':      

    aim=['<30','Medium','Yes','Fair']
    category ='buys_computer'
    
    [data,attribute]=loadDataSet()    
    
    devide_data=devidedata(data, category)
    decision = decide(data, devide_data,category, aim)
    print('so the final decision is: "{0}"').format(decision)
    
