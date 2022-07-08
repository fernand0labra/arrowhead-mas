# Mismatch Analysis System

## Table of Contents
- [Introduction](#introduction)
- [Motivation](#motivation)
- [Research Scenario](#research-scenario)
- [Application System Architecture](#application-system-architecture)
  * [Input Module](#input-module)
  * [Mismatch Check Module](#mismatch-check-module)
  * [Decision Module](#decision-module)
- [Mismatch Check Algorithm](#mismatch-check-algorithm)

## Introduction

System of Systems (SoS) paradigm used together with Internet of Things (IoT) and Cyber-Physical Systems (CPS)  enables the creation of new applications focused in the management of industrial facilities and processes. For the last decade, the Industry 4.0 revolution has been increasingly changing the way factories are managed and understood. The automation of measurement and control over its different parts makes it necessary to exchange information correctly and in real time. Therefore, the communication between devices has to be achieved without any problem for the building of these applications.

During the exchange of information between a consumer and provider systems that want to interact with each other, we can find a certain number of problems that could arise during the exchange of information. One can think of, for example, how the information will be sent in the application layer; different protocols can be used for the transmission of information between one and another. More aspects to take into account are the encoding of the information which the message contains and the way its information is understood; formally, its semantics. 

These problems have to be handled independently and due to the great number of protocols, encodings and semantics possible; the complexity and level of manual work necessary to achieve the integration of a system increases exponentially. However, it is possible to automate the communication of a system with another one in terms of resolving the differences previously mentioned. 

As seen in many other engineering fields, automation is the capability of a system to independently work with others without the intervention of a human and through its implementation, the complexity and cost of the manual work are greatly reduced. The exchange of information is defined by the use of Service Interfaces. Ideally there should be only one for each of the services offered and consumed, but it could be possible that there is more than one for the same service but with different protocol, encoding format, etc.

Nowadays there is a vast number of software solutions and changes can be made in one system without them being reflected in the definition that another one has of its service interface. The heterogeneity of the market opens complex combinations that make it very difficult to integrate or even communicate one system with another. This ability of two or more systems (software components) to cooperate despite differences in language, interface and execution platform can be understood as the interoperability.

Research has been made for solving the mismatches that might appear on a high level of communication such as the transmission protocol or the encoding of the information. However, there are other aspects that have not been studied profoundly and are simply left for the designer of the system to decide, such as semantic standards or ontologies.

## Motivation

As stated before, the use of automation on every engineering field can help reduce the time efforts of the manual work. This can also be applied to interoperability issues by resolving the mismatches between the interfaces that define a system's behavior (i.e. the services that provides and consumes). If a mismatch were to be solved between the exchange of information of two systems by the use of other support system, automation could be granted into the task of interoperability.

Services are defined through the use of an Interface Description Language (IDL), and none completely define all the key elements a logical interface has. Because of this, the identification of mismatches between two interfaces is considered a complex task to generalize. 

The tool that has been built identifies the aspects of the IDL that define the service and compares the differences on both systems wanting to interact between themselves. After this comparison, a level of compatibility and uncertainty of mismatch is computed. Finally, a decision is taken on how to address every mismatch found, if any. The proposed solution was developed with interest of a true integration within the Arrowhead Framework. Due to this, it follows a compliant architecture and definition of what an application system is.

## Research Scenario

A SoS is conformed by a group of systems that interact between one and another building new applications. One real-life example that could be considered is a chemical industrial plant that needs to constantly take information on the temperature and pressure values of the different containers and conduits. 

The majority of systems in this example would be the measuring software that runs over IoT devices distributed throughout each of the containers and conduits of the industry. We can think of a system that builds on the data received from each of them and not only displays a generic view of the plant's status, but also warns about dangerous values that can harm its integrity. The provider system, considered for the service interface definitions, is an IoT temperature sensor. This sensor offers different values based on the unit specified as parameter (Celsius, Kelvin, etc.) in the request.

The research scenario is based on the aforementioned AH local cloud concept. The mandatory core systems including the Service Registry, the Orchestration and Authorization Systems coordinate with the Translator System, the Interface Generator System and the Mismatch Analysis System. This last system refers to the implemented tool of this project and is meant to integrate as a support system of AH. The reason of choosing this framework is to take advantage of the existing SOA aspects that it implements. 

The Mismatch Analysis System is used during the orchestration. It is meant to obtain a compatibility as well as an uncertainty degree on the two service interfaces that consumer and provider consider. If there is more than one service interface for a certain operation ID, the Ochestrator request to the Mismatch Analysis System would contain all the different provider system names that offer that same service. Based on the level of compatibility, the Mismatch Analysis System decides which one matches the most with the consumer (if any) and responds it to the Orchestrator.

<p align="center">
  <img src="https://user-images.githubusercontent.com/70638694/177991585-25eec955-8af1-4fed-b77c-e08548dca7c0.png"/>
</p>

The research is performed in the following way, there are a series of service contract definitions for the same service (e.g. obtain the temperature from the sensor). The difference between them lies in the protocol, encoding, semantics and notation. 

The implemented system is in charge of analysing each of them when a consumer asks for this service and obtain a level of compatibility and uncertainty. Based on it, the system answers to the Orchestrator with an Analysis object containing the performed analysis for a certain system and service. Finally, the analysis is completed with a flag that defines the changes necessary to perform (if any).

## Application System Architecture

The Mismatch Analysis System architecture is built from three modules that independently work together and can be easily substituted as long as they follow the same input/output defined for each of them. The modules are divided as:

- **Input Module**, obtains the SCs that both consumer and all the possible providers consider and parses them into objects for later analysis.
- **Mismatch Check Module**, analyses the information in the SC objects and for each of the providers, fills an Analysis object that contains a quantitative and qualitative value of compatibility and uncertainty as well as information regarding where the specific mismatch took place, what system and what service are being analyzed.
- **Decision Module**, it orders the list of Analysis objects by their compatibility and for each of them decides what the solution to the mismatches can be (if any). The best positive analysis (with solution) is responded to the Orchestrator and thanks to the defined flag, the Orchestrator can understand which system to call afterwards. If there is no positive analysis, an empty Analysis object is responded.

<p align="center">
  <img src="https://user-images.githubusercontent.com/70638694/177991987-4db27317-d831-470c-81ad-652997c0cc70.png" width="600"/>
</p>

### Input Module

The Input module provides the parsed SCs to the Mismatch Check module. For development reasons, it has been decided to keep it as a separate module so that the possible accepted file formats can be augmented. Also for its evolution and management.As mentioned in the Project's scope, the accepted IDLs for the service contracts are CDL and OpenAPI, although it is intended to include also WADL on future work.

The input values of the module are provided by the request of the Orchestrator. They include the service operation identifier and a list of the consumer and providers' system names. From this, the module obtains the SC (if found) from a local database containing all SCs definitions. The module parses information from the SCs into POJO objects by extracting the most important features. In the frame of interoperability as already explained in other sections they would be the communication protocol, the encoding, the semantics and the notation of the message (by analising the payload).

The SC's object schema is based on CDL, as it perfectly describes all the features that a service contract should have. When the SC's interface is not written using CDL, the aspects that cannot be defined are simply left empty. Any language can be integrated with this schema and through the use of the uncertainty value it is possible to analyse the level of information that a language can give with respect to the SC's definition.

<p align="center">
  <img src="https://user-images.githubusercontent.com/70638694/177993785-316be276-19f2-4a5b-95a1-5d02fc60e36c.png" width="800"/>
</p>

### Mismatch Check Module

The Mismatch Check Module performs an analysis over the SC's objects. Generates a quantitative and qualitative level of compatibility between both of them and fills an Analysis object, whose definition can be seen in Figure 3.4 . It also fills information about what system and what operation are being analyzed so that each object can be identified.

<p align="center">
  <img src="https://user-images.githubusercontent.com/70638694/177992330-5478bb37-3cf2-4092-a644-507b0659588b.png" />
</p>

The mismatch map is meant to store a binary value for each of the elements of a SC object indicating if there is or not a mismatch. The same goes for the notation and the uncertainty, with this last one indicating whether there is enough information to decide if a mismatch is happening for an aspect of the SC. The other attributes of the object indicate the quantitative and qualitative values of the compatibility of both service interfaces and the uncertainty of the analysis performed, the system and operation names and the decision flag defining which solution can be taken. 

<p align="center">
  <img src="https://user-images.githubusercontent.com/70638694/177992438-0cf5777a-0f48-42a0-bbc3-ade47d0c0f40.PNG" />
</p>

The notation HashMap is generated dynamically for both the request and response payload, in this way it is possible to not necessarily know the contents of it before both the parsing and the analysis.

<p align="center">
  <img src="https://user-images.githubusercontent.com/70638694/177992454-2381afb1-bd0b-47fa-9148-fe27a6a5984a.PNG" />
</p>

The mismatch and the uncertainty structures get updated by a sequenced check on both consumer and provider SC objects. The analysis begins with the protocol and then for both request and response continues with encoding, semantics and notation. The notation is specifically analysed so that the mismatch is checked for each element or complex element (list, array, etc.).

The protocol and encoding mismatch checks are performed in the same way. For both SC objects it checks the protocol or mediaType respectively (basically the name of the standard that it is using) and in case it is the same checks on the version. Mismatches are reflected in the previous explained structure. 

<p align="center">
  <img src="https://user-images.githubusercontent.com/70638694/177992531-625570d5-fbfb-4b39-b100-e6e8ef1376c8.png" width="500"/>
</p>

Regarding the semantics, as there can be a definition of either standard or ontology in both consumer or provider, a more thorough check has to be made. The analysis begins with the checking of the definition of a standard for the consumer:

* If it does exist, check the appearance of a standard in the provider:
  - If the standard in the provider exists, then it is possible to check whether they have the same name and version and update the mismatch structure in case there is one.
  - In other case, check the appearance of an ontology in the provider. If it exists then there is a mismatch because a standard and an ontology are not compatible semantic wise and if it does not, then there is neither a standard nor an ontology and therefore an uncertainty.
* In other case check the appearance of the ontology in the consumer:
  - If the ontology in the consumer does not exist, then there is neither a standard nor an ontology and therefore an uncertainty.
  - In other case, check the appearance of an ontology in the provider.
    + If it exists, then it is possible to check whether they have the same name and version and update the mismatch structure in case there is one.
    + In other case, check the appearance of the standard in the provider. If it exists, then there is a mismatch because as both standard and ontology are defined. In other case, there is uncertainty on whether both consumer and provider will be able to exchange information.
    
<p align="center">
  <img src="https://user-images.githubusercontent.com/70638694/177992560-b789ce1a-dc81-45ed-ae26-698fb12e8b02.png" width="800"/>
</p>

Finally, the notation is addressed by checking each element of the payload of the consumer or provider. It is not possible to define an uncertainty for the notation as the mismatch in one of the variables (name or type) cannot be always addressed. Figure 3.10 displays the decision diagram of the notation.

* The types will be checked in the following cases:
  - If the name of the element is the same as the name defined in the other Interface Description.
  - If the name of the element is the same as variation defined in the other Interface Description.
  - If the variation of the element is the same as the name defined in the other Interface Description.
  - If the variation of the element is the same as the variation defined in the other Interface Description.
* In any other case, there is a mismatch in the notation. The name of the variable is not recognized in one of the descriptions or the type of the variable is different.

<p align="center">
  <img src="https://user-images.githubusercontent.com/70638694/177992631-e69eb6b0-f59a-47f1-afbc-19986189b14d.png" width="400"/>
</p>

### Decision Module

The Decision Module, once the compatibility and identification of mismatches have been made, analyses the mismatch, uncertainty and notation maps. The level of compatibility and uncertainty measured as a quantitative or qualitative value is not enough to decide what action to perform but are used instead for ordering the list of the provider systems.

The set flag is based on the different actions that the Orchestrator can make by the use of other systems. This actions include:
* OK - Fully compatible, no actions are required on the SC of the consumer in order to perform the exchange of information.
* NOT_OK - Not compatible, due to a high difference on the SC the interaction between consumer and provider is not possible.
* ALTER_T - Call the Translator, performing a translation on the communication protocol between consumer and provider.
* ALTER_G - Call the Interface Generator System, performing a translation on the encoding or/and semantics between consumer and provider.

The modularity is again applied so that as new solutions appear for interoperability issues, new flags can be added. It is also important to consider that because the SC's objects are based on CDL every new solution addressing a specific mismatch will always be able to be checked on this objects, as they perfectly describe all information regarding a SC. 

For the Orchestrator to understand why a system is sending a certain flag and what should it do with it, it's necessary to add a new section to its content. We consider that the purpose of this project is to expand the Arrowhead Framework by the use of both CDL and the Mismatch Analysis System.

The following figures show a sequence diagram of the interaction of the different AH systems for the OK flag, the NOT_OK flag and the ALTER_G flag. As seen below, the OK flag acts as if the system had not been called, the NOT_OK flag denies the service consumption and the ALTER_G flag makes the consumer use the Interface Generator System for communicating with the provider.

<p align="center">
  <img src="https://user-images.githubusercontent.com/70638694/177993632-ce20275e-79bb-4761-9329-d7b66818f130.png" />
</p>

<p align="center">
  <img src="https://user-images.githubusercontent.com/70638694/177993645-23227db6-53c5-488d-82d8-4e5033bf602b.png" />
</p>

<p align="center">
  <img src="https://user-images.githubusercontent.com/70638694/177994109-91cf0c36-b91c-48cb-8035-2aad4211d3f4.png" />
</p>

## Mismatch Check Algorithm

The algorithm for the calculation of both the compatibility and uncertainty quantitative values is based on the same mathematics concept, i.e. the composition of polynomial functions. Both compatibility and uncertainty functions are a composition of the calculations for each of the aspects of interoperability. This aspects are defined in the Analysis object's maps.

The function for compatibility calculation is $f(x,w,c) = [p(x,w) + e(x,w) + sm(x,w,c) + n(x,w)] * 100 \in [0, 100]$. Each of the functions that is composed of is defined as the product of the mismatch appearance (binary value) with the associated weight for that certain mismatch.

The weights are given as a parameter value to the function too, as it is intended in future work to use some form of Machine Learning for weight updating and more accurate compatibility and uncertainty values.

The protocol function uses the set $x=\{protocol, version\} \in \{0, 1\}$
$$p(x, w) = w_0 * x_0 + w_1 * x_1$$
    
The encoding function uses the set $x=\{mediaTypeReq, versionReq, mediaTypeRes, versionRes\} \in \{0, 1\}$ as well as weights for both response and request.
$$e(x, w) = w_0 * x_0 + w_1 * x_1 + w_2 * x_2 + w_3 * x_3$$
        
The semantics function is composed at the same time of a standard and an ontology functions. Both of them use the set $x=\{standardName, standardVersion, ontologyName, ontologyVersion\} \in \{0, 1\}$ as well a set of weights and an array of condition values. Both functions have the same range as the semantics function, but their sum cannot exceed it. 

This behavior has been thought so that it can be applied for both request and response with one defining a standard in the request and an ontology in the response or viceversa.
$$sm(x, w, c) = st(x, w, c) + o(x, w, c)$$

$$
\begin{align}
  st(x, w, c) = & & & & \\
  & (c_0 = 0 \wedge c_1 = 1 \wedge (c_2 = 0 \vee c_3 = 0)) & \rightarrow & & 0 \\
  & (c_0 = 1 \wedge c_1 = 0 \wedge (c_2 = 0 \vee c_3 = 0)) & \rightarrow & & 0 \\
  & otherwise & \rightarrow & & w_0 * x_0 + w_1 * x_1 + w_2 * x_2 + w_3 * x_3
\end{align}
$$

$$
\begin{align}
  o(x, w, c) = & & & & \\
  & ((c_0 = 0 \vee c_1 = 0) \wedge c_2 = 0 \wedge c_3 = 1) & \rightarrow & & 0 \\
  & ((c_0 = 0 \vee c_1 = 0) \wedge c_2 = 1 \wedge c_3 = 0) & \rightarrow & & 0 \\
  & otherwise & \rightarrow & & w_0 * x_0 + w_1 * x_1 + w_2 * x_2 + w_3 * x_3
\end{align}
$$

The condition values are used for not adding a compatibility or uncertainty value when a certain case is given. They are:

* $c_0$ - The consumer does not define a standard.
* $c_1$ - The provider does not define a standard.
* $c_2$ - The consumer does not define an ontology.
* $c_3$ - The provider does not define an ontology.

The cases defined in the semantics function are (1) when the consumer defines a standard and the provider defines an ontology or (2) viceversa and (3) when the consumer defines an ontology and the provider defines a standard or (4) viceversa. This applies to both request and response.

Finally, the notation function uses the set $x={name, type} \in {0, 1}$ as well as a set of weights. The function is defined as the sum over the N elements of the payload of a polynomial function.
$$n(x, w, N) = \sum_{n=0}^{N-1} w_n * x_n + w_{n+1} * x_{n+1}$$

For the uncertainty quantitative value the same functions are used except the notation one. This is because the notation is specific for each request and does not relate to the quantity of information displayed by the IDL used.

The function used is $g(x,w,c) = [p(x,w) + e(x,w) + sm(x,w)] * 100 \in [0, 100]$.

How the algorithm is implemented, the level of compatibility defines how close the SC considered for the consumer resembles the one considered by the provider. There could be a special case in which a SC has a mismatch only in notation but is similar in all of the other aspects. In this specific case, the compatibility value would be used only for the ordering of the analysis objects and the NOT_OK flag would be what makes the mismatch analysis system check the next SC defined (if any) or deny the consumption of the service.

There are five different qualitative values that are considered from the quantitative value obtained:
* **nil**, there is a complete mismatch between both SCs. The value is associated with the NOT_OK flag.
* **low**, there is a low compatibility between both SCs. This usually indicates that there are at least three types of mismatches.
* **medium**, there is a medium compatibility between both SCs. This usually indicates that there are at least two types of mismatches.
* **high**, there is a high compatibility between both SCs. This usually indicates that there is at least on type of mismatch.
* **absolute**, both SCs are exactly the same in terms of interoperability.

Qualitative values are always subjective to the designer of the system. In this case those values are considered but they are not based on any standard or previous definition.
