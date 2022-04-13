# My-UJ-Computer-Science-projects

# PROJECT 1

# Network Project
Identify a problem that is relevant to the Fourth Industrial Revolution. Implement a Java
Graphical User Interface-based client (Swing, JavaFX or Web-based with Javascript) that
collects image-based data (such as images or video) from a particular source (such as stored
or captured images or video from a camera or file using Webcam Capture) and sends the data
to the provided web API for processing. 
Web APIs provide a standard means of interoperating
between different software applications, running on a variety of platforms or frameworks. Web
APIs are characterised by their great interoperability and extensibility, as well as their machine 
processable descriptions thanks to the use of JSON (sometimes XML).
They can be combined
in a loosely coupled way in order to achieve complex operations by communicating over a
network using the HTTP protocol. You need to design and implement a client (in Java) that
consumes an API we have created (on the localhost, port number 5000) with the following
functions.
The service calls may be accessed using the URL: /api/*, where * is the function
you would like to consume, eg. /api/GrayScale (one of each category must be used):
# Pre-processing:
GrayScale(image) A function that transforms colour images into an intensity map or
  greyscale image.
Rotate(image) A function that changes the orientation of an input image based on degrees
specified.
Erosion(image) A morphological function that shapes an input image based on some structuring element called a kernel.
Dilation(image) A morphological function that exaggerates certain features based on a
structuring element.
Crop(image) A function that returns the sub-region of the input image
# Feature extraction:
 CannyFeatures(image) A function that detects significant gradient changes using a multistage approach. Returns a JSON object of upper and lower bounds eg, {”py/set”:
 [”py/tuple”: [60, 121]]}, you must only extract the values [60,121].
 Canny(image) A function that returns an image with the canny edges drawn on it.
 FastFeatures(image) A function that detects corners in the image by finding contiguous
 pixels in a circle by analysing non-corner surrounding pixels. Returns a JSON object,
 representing the number of fast features found within the image eg, {”py/set”: [1610]},
 you are required to only extract the value.
Fast(image) A function that returns an image with the fast corners drawn on it

# PROJECT 2
The project demonstrates your understanding and proficiency with the concepts discussed
in class and how they can be applied within a specific domain to solve a problem. You might
need to make use of data structures we have not yet covered in class. In this case you will
need to research the implementation of the appropriate data structure.
# Theme: Blockchains with Graphs
The theme for the Project are network-based Blockchains. A blockchain is a collection of
records (called blocks), where each block links to the previous block using cryptography. Each
block contains cryptographic hash, timestamp and data relevant to its application. Think of
it as a distributed ledger where instead of information being stored in a database it is stored
on the blockchain. 
