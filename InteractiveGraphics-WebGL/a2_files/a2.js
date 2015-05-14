/*
 * a2.js
 * CSCI B481 Interactive Graphics
 * Anthony Powell - Spring 2015
 * anjopowe
 * 2/27/2015
 */

var gl = null;
var gModelViewInTheShader = new Array()
var gProjectionInTheShader = new Array();
var gGLSLprogram = new Array();
var gBufferId = new Array();

var gLineVertices = [
     -1.0, -1.0,
      0.0,  1.0
];

var gMmouseDown = false;  
var gLastMouseXdown = -1;
var gLastMouseYdown = -1;
var gLastMouseXup = -1;
var gLastMouseYup = -1;
var gCanvas = null;

var i = 0;    // canvas refresh counter


//variables needed
var v1Prox = false;
var v2Prox = false;
var vGProx = false; //true if close to either vertex
var lProx = false;
var distance1 = 100;
var distance2 = 100;
var distanceLine = 100;
var v1X = gLineVertices[0];
var v1Y = gLineVertices[1];
var v2X = gLineVertices[2];
var v2Y = gLineVertices[3];
var proxValue = 10; //test for proximity of 10 pixels
var vEdit = false; //vertex editor mode 
var lEdit = false; //line editor mode
var moved = false; //has mouse been moved since last click




// ------------------------------------------------------------
window.onload = function myMainProgram() {
    var lLabel = document.getElementById( "debug-label" )
    lLabel.innerHTML = "testing for WebGL"

    if (!window.WebGLRenderingContext) {
        lLabel.innerHTML = "No WebGL Rendering Context available in your web browser:<br>" +
            "window.WebGLRenderingContext = {"+JSON.stringify(window.WebGLRenderingContext)+"}";
        return;
    } else {
        lLabel.innerHTML = "WebGL Rendering Context found:<br>" +
            "window.WebGLRenderingContext = {"+JSON.stringify(window.WebGLRenderingContext)+"}";
    }

    gCanvas = document.getElementById( "gl-canvas" );
    
    var lWebGLnames = ["webgl", "experimental-webgl", "webkit-3d", "moz-webgl"];
    for (var ii = 0; ii < lWebGLnames.length; ++ii) {
        lLabel.innerHTML = "Querying for {"+lWebGLnames[ii]+"} context."
    
        try {
            gl = gCanvas.getContext(lWebGLnames[ii]);
        } catch(e) {
            alert("Error creating WebGL Context: " + e.toString());
            return;
        }
        if (gl) {
            break;
        }
    }
    if ( !gl ) {
            alert("Unable to create WebGL Context.")
            return;
    }

    lLabel.innerHTML = "Obtained WebGL context."


    

    gl.viewport( 0, 0, gCanvas.width, gCanvas.height );
    gl.clearColor( 1.0, 1.0, 1.0, 1.0 );

    gGLSLprogram.push ( initShaders( gl, "vertex-shader", "fragment-shader-highlight" ) )
    gl.useProgram( gGLSLprogram[0] );
    
    gBufferId.push ( gl.createBuffer() );
    gl.bindBuffer( gl.ARRAY_BUFFER, gBufferId[0] );
    gl.bufferData( gl.ARRAY_BUFFER, new Float32Array(gLineVertices), gl.STATIC_DRAW );
    gBufferId[0].itemSize = 2;
    gBufferId[0].numItems = 2;

    gModelViewInTheShader.push ( gl.getUniformLocation( gGLSLprogram[0], "modelViewMatrix" ) );
    gProjectionInTheShader.push ( gl.getUniformLocation( gGLSLprogram[0], "projectionMatrix" ) );
    

    colorInFragmentShader = gl.getUniformLocation(gGLSLprogram[0], "HighlightColor");
 

    // connect mouse handlers:
    gCanvas.onmousedown = handleMouseDown;
    gCanvas.onmouseup = handleMouseUp;
    gCanvas.onmousemove = handleMouseMotion;

    // to animate, use this:
    refreshCanvas();
    // to draw only once, you would use this instead:
    // drawContent();

    lLabel.innerHTML = "B481 - Assignment 2 - Spring 2015."
} // end of function myMainProgram()




// ------------------------------------------------------------
function drawContent() {

    var lLabel = document.getElementById( "debug-label" );
    var lVertexCoordStrings = ""
    
    // we're drawing in a coordinate system that is identical to viewport coordinates,
    //  therefore stop drawing if any of the coordinates have negative values:
    var lStopDrawing = false;
    for (var j=0; j<gLineVertices.length; j++) {
        lVertexCoordStrings = lVertexCoordStrings + " " + gLineVertices[j] + ""
        if (gLineVertices[j]<0.0) {
            lStopDrawing = true;
        }
    }
// lLabel.innerHTML = " refreshCanvas "+i+" mouseDown("+gLastMouseXdown+","+gLastMouseYdown+")"+" mouseUp("+gLastMouseXup+","+gLastMouseYup+") vertices("+lVertexCoordStrings+")" 
 lLabel.innerHTML = " refreshCanvas "+i+" mouseDown("+gLastMouseXdown+","+gLastMouseYdown+")"+" mouseUp("+gLastMouseXup+","+gLastMouseYup+") vertices("+lVertexCoordStrings+"), vProx = "+vGProx+",lProx= "+lProx+ "distance1= " + distance1+ "v1X = " + v1X
   
    i++;
    if (lStopDrawing==false) {
    
        gl.useProgram( gGLSLprogram[0] );
    
        var lModelViewMatrix = [
           1.0, 0.0, 0.0, 0.0, 
           0.0, 1.0, 0.0, 0.0,
           0.0, 0.0, 1.0, 0.0,
           0.0, 0.0, 0.0, 1.0
        ];
        gl.uniformMatrix4fv( gModelViewInTheShader[0], false, new Float32Array(lModelViewMatrix) );
        gl.uniformMatrix4fv( gProjectionInTheShader[0], false,
            new Float32Array(myOrtho2D(0.0, gCanvas.width, gCanvas.height, 0.0))
        );
        
        var lColor;
        if (vEdit) {
            lColor = [0.1, 1.0, 0.3]; //green
        } else if (lEdit) { 
            lColor = [0.3, 0.1, 1.0]; //blue
        } else {
            lColor = [1.0, 0.1, 0.3]; //red
        }
        gl.uniform3fv( colorInFragmentShader,  new Float32Array( lColor ));
    
        gl.bindBuffer( gl.ARRAY_BUFFER, gBufferId[0] );
        gl.bufferData( gl.ARRAY_BUFFER, new Float32Array(gLineVertices), gl.STATIC_DRAW );
    
        vPosition = gl.getAttribLocation( gGLSLprogram[0], "vPosition" );
        gl.vertexAttribPointer( vPosition, 2, gl.FLOAT, false, 0, 0 );
        gl.enableVertexAttribArray( vPosition );
    
        gl.clear( gl.COLOR_BUFFER_BIT );
        gl.drawArrays(gl.LINES, 0, 2);
    } // if
} // end of function drawContent()


// ------------------------------------------------------------
function myOrtho2D(left,right,bottom,top) {
    var near = -1;
    var far = 1;
    var rl = right-left;
    var tb = top-bottom;
    var fn = far-near;
    // the returned matrix is defined "transposed", i.e. the last row
    //   is really the last column as used in matrix multiplication:
    return [        2/rl,                0,              0,  0,
                       0,             2/tb,              0,  0,
                       0,                0,          -2/fn,  0,
        -(right+left)/rl, -(top+bottom)/tb, -(far+near)/fn,  1];
}



// ------------------------------------------------------------
// mouse event functions
// 
// the functions below work with mouse coordinates relative to the canvas, as from:
//  http://miloq.blogspot.com/2011/05/coordinates-mouse-click-canvas.html
// ------------------------------------------------------------


function handleMouseDown(event) {
    var lLabel = document.getElementById( "debug-label-2" );
    gMmouseDown = true;
    var oldX = gLastMouseXdown;
    var oldY = gLastMouseYdown; 

    if (event.x != undefined && event.y != undefined) {
        gLastMouseXdown = event.x + document.body.scrollLeft +
                            document.documentElement.scrollLeft;
        gLastMouseYdown = event.y  + document.body.scrollTop +
                            document.documentElement.scrollTop;
        lLabel.innerHTML = "event.x=" + event.x + " event.y=" + event.y;
    } else {
        // Firefox method to get the position
        gLastMouseXdown = event.clientX + document.body.scrollLeft +
                            document.documentElement.scrollLeft;
        gLastMouseYdown = event.clientY + document.body.scrollTop +
                            document.documentElement.scrollTop;
        lLabel.innerHTML = "event.clientX=" + event.clientX + " event.clientY=" + event.clientY;
    }

    gLastMouseXdown -= gCanvas.offsetLeft;
    gLastMouseYdown -= gCanvas.offsetTop;
    lLabel.innerHTML += " handleMouseDown: old("+oldX+","+oldY+")"+" new("+gLastMouseXdown+","+gLastMouseYdown+")"

    if (lProx) {
        lEdit = true;
    } else if (vGProx || !moved) {
        vEdit = true;
    } else {
        gLineVertices = [
            oldX, oldY,
            gLastMouseXdown, gLastMouseYdown
        ];   
    }

//    refreshCanvas();
}

// ------------------------------------------------------------


function handleMouseUp(event) {
    gMmouseDown = false;
    moved = false;

    if (event.x != undefined && event.y != undefined) {
        gLastMouseXup = event.x + document.body.scrollLeft +
                            document.documentElement.scrollLeft;
        gLastMouseYup = event.y  + document.body.scrollTop +
                            document.documentElement.scrollTop;
    } else {
        // Firefox method to get the position
        gLastMouseXup = event.clientX + document.body.scrollLeft +
                            document.documentElement.scrollLeft;
        gLastMouseYup = event.clientY + document.body.scrollTop +
                            document.documentElement.scrollTop;
    }

    gLastMouseXup -= gCanvas.offsetLeft;
    gLastMouseYup -= gCanvas.offsetTop;


    if (vEdit) {
        vEdit = false;
    } else if (lEdit) {
        lEdit = false;
    }

}

// ------------------------------------------------------------
function handleMouseMotion(event) {
    var lX;
    var lY;

    moved = true;

    if (event.x != undefined && event.y != undefined) {
        lX = event.x + document.body.scrollLeft +
                            document.documentElement.scrollLeft;
        lY = event.y + document.body.scrollTop +
                            document.documentElement.scrollTop;
    } else {
        // Firefox method to get the position
        lX = event.clientX + document.body.scrollLeft +
                            document.documentElement.scrollLeft;
        lY = event.clientY + document.body.scrollTop +
                            document.documentElement.scrollTop;
    }

    lX -= gCanvas.offsetLeft;
    lY -= gCanvas.offsetTop;

    if (gMmouseDown) {
        var newX = lX;
        var newY = lY;
        var deltaX = newX - gLastMouseXdown;
        var deltaY = newY - gLastMouseYdown;

        // here compute anything that may use mouse "down" motion
        // ... using deltaX and deltaY
        gLastMouseXdown = newX;
        gLastMouseYdown = newY;


        //first vertex values
        v1X = gLineVertices[0];
        v1Y = gLineVertices[1];
        //second vertex values
        v2X = gLineVertices[2];
        v2Y = gLineVertices[3];

        if (vGProx) {
            vEdit = true;
            lEdit = false;
            if (v1Prox) { //change first vertex
              v1X = v1X + deltaX;
              v1Y = v1Y + deltaY;
            } else { //change second vertex
              v2X = v2X + deltaX;
              v2Y = v2Y + deltaY;
            }
            gLineVertices = [
                v1X, v1Y,
                v2X, v2Y
            ];
        } else if (lProx) {
            lEdit = true;
            vEdit = false;
            v1X = v1X + deltaX;
            v1Y = v1Y + deltaY;
            v2X = v2X + deltaX;
            v2Y = v2Y + deltaY;
            gLineVertices = [
                v1X, v1Y,
                v2X, v2Y
            ];
        } else {
            lEdit = false;
            vEdit = false;
        }
    } else {
        
        lEdit = false;
        vEdit = false;

        gLastMouseXup = lX;
        gLastMouseYup = lY;

        /* Determine proximity of mouse to either vertex while mouse is up */

    	//first vertex values
    	v1X = gLineVertices[0];
    	v1Y = gLineVertices[1];
    	//second vertex values
    	v2X = gLineVertices[2];
    	v2Y = gLineVertices[3];


    	distance1 = Math.sqrt( Math.pow(lX - v1X, 2) + Math.pow(lY - v1Y, 2) );
    	distance2 = Math.sqrt( Math.pow(lX - v2X, 2) + Math.pow(lY - v2Y, 2) );
        var minX = Math.min(v1X, v2X);
        var minY = Math.min(v1Y, v2Y);
        var maxX = Math.max(v1X, v2X);
        var maxY = Math.max(v1Y, v2Y);
    	
    	//test proximity to vertices
    	if (distance1 <= proxValue) {
    		v1Prox = true;
    		v2Prox = false;
            vGProx = true;
    	} else if (distance2 <= proxValue) {
    		v2Prox = true;
    		v1Prox = false;
            vGProx = true;
    	} else {
    		v1Prox = false;
    		v2Prox = false;
            vGProx = false;
    	}
        

    	/* Determine proximity of mouse to line itself if no vertice is near the mouse */

    	if (!(v1Prox || v2Prox)) { //if mouse not near either vertex

    		distanceLine = Math.abs((v2X-v1X)*(v1Y-lY) - (v1X-lX)*(v2Y-v1Y)) / Math.sqrt( Math.pow(v2X-v1X,2) + Math.pow(v2Y-v1Y,2));
    		
    		if (distanceLine < 5 && lX >= minX && lX <= maxX && lY >= minY && lY <= maxY) {
    			lProx = true;
    		} else {
    			lProx = false;
    		}
    		
    	}
        
    }      
}





// ------------------------------------------------------------
// animation utility functions
// ------------------------------------------------------------
function refreshCanvas() {
    requestAnimationFrame(refreshCanvas);
    drawContent();
}

// ------------------------------------------------------------
window.requestAnimFrame = (function() {
  return window.requestAnimationFrame ||
         window.webkitRequestAnimationFrame ||
         window.mozRequestAnimationFrame ||
         window.oRequestAnimationFrame ||
         window.msRequestAnimationFrame ||
         function(/* function FrameRequestCallback */ callback, /* DOMElement Element */ element) {
           window.setTimeout(callback, 1000/60);
         };
})();


// ------------------------------------------------------------
// shaders utility functions
// ------------------------------------------------------------
function initShaders( gl, vertexShaderId, fragmentShaderId ) {
    var vertShdr;
    var fragShdr;

    var vertElem = document.getElementById( vertexShaderId );
    if ( !vertElem ) { 
        alert( "Unable to load vertex shader " + vertexShaderId );
        return -1;
    }
    else {
        vertShdr = gl.createShader( gl.VERTEX_SHADER );
        gl.shaderSource( vertShdr, vertElem.text );
        gl.compileShader( vertShdr );
        if ( !gl.getShaderParameter(vertShdr, gl.COMPILE_STATUS) ) {
            var msg = "Vertex shader failed to compile.  The error log is:"
        	+ "<pre>" + gl.getShaderInfoLog( vertShdr ) + "</pre>";
            alert( msg );
            return -1;
        }
    }

    var fragElem = document.getElementById( fragmentShaderId );
    if ( !fragElem ) { 
        alert( "Unable to load vertex shader " + fragmentShaderId );
        return -1;
    }
    else {
        fragShdr = gl.createShader( gl.FRAGMENT_SHADER );
        gl.shaderSource( fragShdr, fragElem.text );
        gl.compileShader( fragShdr );
        if ( !gl.getShaderParameter(fragShdr, gl.COMPILE_STATUS) ) {
            var msg = "Fragment shader failed to compile.  The error log is:"
        	+ "<pre>" + gl.getShaderInfoLog( fragShdr ) + "</pre>";
            alert( msg );
            return -1;
        }
    }

    var program = gl.createProgram();
    gl.attachShader( program, vertShdr );
    gl.attachShader( program, fragShdr );
    gl.linkProgram( program );
    
    if ( !gl.getProgramParameter(program, gl.LINK_STATUS) ) {
        var msg = "Shader program failed to link.  The error log is:"
            + "<pre>" + gl.getProgramInfoLog( program ) + "</pre>";
        alert( msg );
        return -1;
    }

    return program;
}


// ------------------------------------------------------------
