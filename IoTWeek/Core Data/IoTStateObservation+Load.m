//
//  IoTStateObservation+Load.m
//  IoTWeek
//
//  Created by Thomas Gilbert on 16/05/14.
//  Copyright (c) 2014 ITAdvice. All rights reserved.
//

#import "IoTStateObservation+Load.h"
#import "Property+Load.h"
#import "IoTEntity+Load.h"

@implementation IoTStateObservation (Load)

+ (void)loadIoTStateObservationsFromArray:(NSArray *)iotStateObservations
                    forIoTEntityWithAbout:(NSString *)iotEntityAbout
                   forPropertiesWithAbout:(NSString *)propertiesAbout
                      usingManagedContext:(NSManagedObjectContext *)context
{
    Property *property = [Property propertyWithAbout:propertiesAbout forIoTEntityWithAbout:iotEntityAbout usingManagedContext:context];
    
    NSFetchRequest *request = [NSFetchRequest fetchRequestWithEntityName:@"IoTStateObservation"];
    request.predicate = [NSPredicate predicateWithFormat:@"cnProperty.cnAbout = %@ AND cnProperty.cnIoTEntity.cnAbout = %@ AND cnPhenomenonTime = %@ AND cnResultTime = %@", property.cnAbout, property.cnIoTEntity.cnAbout, [observation valueForKeyPath:@"PhenomenonTime"], ];
    
    NSError *error;
    NSArray *matches = [context executeFetchRequest:request error:&error];
    
    // TODO: Move these stupid assignments elsewhere
    
    if (!matches || error || ([matches count] > 1)) {
        // handle error
    } else if ([matches count]) {
        // Use existing object, and update attributes
        property = [matches firstObject];
        
        property.cnAbout = permId;
        property.cnDataType = [propertyDictionary valueForKeyPath:@"DataType"];
    
    // TODO: Do something else here!
    NSMutableSet *iotStateObservationSet = [[NSMutableSet alloc] init];
    
    if ([property.cnIoTStateObservation count] == 1)
        property.cnIoTStateObservation = nil;
    //else if ([property.cnIoTStateObservation count] > 1)
    //    return;
    
    for (NSString *observation in iotStateObservations) {
        IoTStateObservation *myObservation = [NSEntityDescription insertNewObjectForEntityForName:@"IoTStateObservation"
                                                                           inManagedObjectContext:context];
        
        //NSDateFormatter *dateFor = [[NSDateFormatter alloc] init];
        //[dateFor setDateFormat:@"yyyy-MM-dd HH:mm:ss"];
        
        myObservation.cnValue = [observation valueForKeyPath:@"Value"];
        myObservation.cnResultTime = [observation valueForKeyPath:@"ResultTime"];//[dateFor dateFromString:[observation valueForKeyPath:@"ResultTime"]];
        myObservation.cnPhenomenonTime = [observation valueForKeyPath:@"PhenomenonTime"]; // [dateFor dateFromString:[observation valueForKeyPath:@"PhenomenonTime"]];
        myObservation.testdate = [NSDate date];
        
        // NSLog(@"Loading phoenomeontime: %@", [observation valueForKeyPath:@"PhenomenonTime"]);
        
        [iotStateObservationSet addObject:myObservation];
    }
    
    property.cnIoTStateObservation = iotStateObservationSet;
    
    NSError *error;
    [context save:&error];
    if (error)
        abort();
}

-(void)setTestdate:(NSDate *)testdate
{
    NSDateFormatter *dateFor = [[NSDateFormatter alloc] init];
    [dateFor setDateFormat:@"yyyy-MM-dd HH:mm:ss"];
    
    NSLog(@"Datetest: %@", [dateFor stringFromDate:testdate]);
}

@end
