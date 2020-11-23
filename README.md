# MyPack

## Project Proposal

My project will be an application for hikers and backpackers. It will allow users to list the
items in their packs and keep track of the weight and cost of their gear. Users can keep track of
more than one pack, to account for different seasons or types of trips. This project is of interest to me 
because I'm a backpacker myself.

In each pack, users will be able to group their gear into custom categories, for example:
- Shelter
- Cooking equipment
- First aid
- Clothes
- Etc.

Each item of gear will have a name, description, cost, and weight. Further, users can mark an item
as **worn** or **consumable**. A **worn** item is an item the user wears on their person or otherwise carries
outside their pack such as clothing, a watch, or hiking poles. A **consumable** item is something that a user 
will consume over the course of a trip such as food, water, or fuel. This is important because backpackers
separate the weight of their pack into **total weight** and **base weight** (total weight minus 
the weight of worn and consumable items), with base weight considered more relevant.

## User Stories
- Phase 1
    - As a user, I want to be able to create a new pack (**IMPLEMENTED**)
    - As a user, I want to be able to edit a pack's name and description (**IMPLEMENTED**)
    - As a user, I want to be able to add a new category to a pack (**IMPLEMENTED**)
    - As a user, I want to be able to add items to a category (**IMPLEMENTED**)
    - As a user, I want to be able to view my packs (**IMPLEMENTED**)
    - As a user, I want to be able to view a single pack and see the items in it grouped by category (**IMPLEMENTED**)
    - As a user, I want to be able to see a pack's total weight and base weight (**IMPLEMENTED**)
    - As a user, I want to be able to delete a pack (**IMPLEMENTED**)
    - As a user, I want to be able to edit a category's name and description
    - As a user, I want to be able to delete a category from a pack
    - As a user, I want to be able to change an item's category
    - As a user, I want to be able to delete an item from a category
- Phase 2
    - As a user, I want the application to automatically save my data when I quit (**AMENDED - SEE PHASE 3**)
    - As a user, I want the application to automatically load my data on startup (**AMENDED - SEE PHASE 3**)
- Phase 3
    - As a user, I want to be able to choose to save my data to a file (**IMPLEMENTED**) 
    - As a user, I want to be have the option to load a saved file (**IMPLEMENTED**)
    - As a user, I want to see a graph of each pack's total weight by category (**IMPLEMENTED**)
    
## Phase 4
### Task 2
I chose to include a type hierarchy. The classes are: AbstractEntry, PackList, PackItem, and Pack. PackList and
PackItem both override the abstract methods getTotalWeight, getBaseWeight, and getCost from AbstractEntry. Pack extends
PackList and has the same functionality, but inserts a default child entry in its constructor.  

