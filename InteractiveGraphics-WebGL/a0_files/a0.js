// ------------------------------------------------------------
// a0.js
// CSCI B481 Interactive Graphics
// Anthony Powell
// anjopowe
// 
// ------------------------------------------------------------

var gl = null;
var gModelViewInTheShader = new Array()
var gProjectionInTheShader = new Array();
var gGLSLprogram = new Array();
var gBufferId = new Array();

var linevertices = [
      0.0,  0.5,
      0.5,  -0.5
];

var gMmouseDown = false;  
var gLastMouseXdown = null;
var gLastMouseYdown = null;
var gLastMouseXup = null;
var gLastMouseYup = null;
var lCanvas;

var i = 0;    // canvas refresh counter


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

    var lCanvas = document.getElementById( "gl-canvas" );
    
    var lWebGLnames = ["webgl", "experimental-webgl", "webkit-3d", "moz-webgl"];
    for (var ii = 0; ii < lWebGLnames.length; ++ii) {
        lLabel.innerHTML = "Querying for {"+lWebGLnames[ii]+"} context."
    
        try {
            gl = lCanvas.getContext(lWebGLnames[ii]);
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


    

    gl.viewport( 0, 0, lCanvas.width, lCanvas.height );
    gl.clearColor( 1.0, 1.0, 1.0, 1.0 );

    gGLSLprogram.push ( initShaders( gl, "vertex-shader", "fragment-shader-highlight" ) )
    gl.useProgram( gGLSLprogram[0] );
    
    gBufferId.push ( gl.createBuffer() );
    gl.bindBuffer( gl.ARRAY_BUFFER, gBufferId[0] );
    gl.bufferData( gl.ARRAY_BUFFER, new Float32Array(linevertices), gl.STATIC_DRAW );
    gBufferId[0].itemSize = 2;
    gBufferId[0].numItems = 2;

    gModelViewInTheShader.push ( gl.getUniformLocation( gGLSLprogram[0], "modelViewMatrix" ) );
    gProjectionInTheShader.push ( gl.getUniformLocation( gGLSLprogram[0], "projectionMatrix" ) );
    

    colorInFragmentShader = gl.getUniformLocation(gGLSLprogram[0], "HighlightColor");
 

    // connect mouse handlers:
    lCanvas.onmousedown = handleMouseDown;
    lCanvas.onmouseup = handleMouseUp;
    lCanvas.onmousemove = handleMouseMotion;

    // to animate, use this:
    refreshCanvas();
    // to draw only once, you would use this instead:
    // drawContent();

    lLabel.innerHTML = "B481 - Assignment 0 - Spring 2015."
};



// ------------------------------------------------------------
function drawContent() {

    var lLabel = document.getElementById( "debug-label" );
    lLabel.innerHTML = " refreshCanvas "+i+" mouseDown("+gLastMouseXdown+","+gLastMouseYdown+")"+" mouseUp("+gLastMouseXup+","+gLastMouseYup+")"
    i++;

    gl.useProgram( gGLSLprogram[0] );

    var lModelViewMatrix = [
       1.0, 0.0, 0.0, 0.0, 
       0.0, 1.0, 0.0, 0.0,
       0.0, 0.0, 1.0, 0.0,
       0.0, 0.0, 0.0, 1.0
    ];
    gl.uniformMatrix4fv( gModelViewInTheShader[0], false, new Float32Array(lModelViewMatrix) );
    gl.uniformMatrix4fv( gProjectionInTheShader[0], false, new Float32Array(myOrtho2D(-1,1,-1,1)) );

    var lColor = [1.0, 0.1, 0.3];
    gl.uniform3fv( colorInFragmentShader,  new Float32Array( lColor ));

    gl.bindBuffer( gl.ARRAY_BUFFER, gBufferId[0] );
    gl.bufferData( gl.ARRAY_BUFFER, new Float32Array(linevertices), gl.STATIC_DRAW );

    vPosition = gl.getAttribLocation( gGLSLprogram[0], "vPosition" );
    gl.vertexAttribPointer( vPosition, 2, gl.FLOAT, false, 0, 0 );
    gl.enableVertexAttribArray( vPosition );

    gl.clear( gl.COLOR_BUFFER_BIT );
    gl.drawArrays(gl.LINES, 0, 2);
}


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
// the functions below work with mouse coordinates relative to the web page.
//  to work with mouse coordinates relative to the canvas, you may want to check here:
//  http://miloq.blogspot.com/2011/05/coordinates-mouse-click-canvas.html
// ------------------------------------------------------------


function handleMouseDown(event) {
    gMmouseDown = true;
    oldx=2*gLastMouseXdown/512-1.03 ;
    oldy=2*(512-gLastMouseYdown)/512-0.69 ;
    gLastMouseXdown = event.clientX;
    gLastMouseYdown = event.clientY;
    xnew= 2*event.clientX/512-1.03 ;
    ynew= 2*(512-event.clientY)/512-0.69 ;
    linevertices = [
        oldx, oldy,
        xnew, ynew
    ];
//    refreshCanvas();
}

// ------------------------------------------------------------
function handleMouseUp(event) {
    gMmouseDown = false;
    gLastMouseXup = event.clientX;
    gLastMouseYup = event.clientY;
}

// ------------------------------------------------------------
function handleMouseMotion(event) {
    if (gMmouseDown) {
        var newX = event.clientX;
        var newY = event.clientY;
        var deltaX = newX - gLastMouseXdown
        var deltaY = newY - gLastMouseYdown;
        // here compute anything that may use mouse "down" motion
        // ... using deltaX and deltaY
       
        gLastMouseXdown = newX
        gLastMouseYdown = newY;
    } else {
        gLastMouseXup = event.clientX;
        gLastMouseYup = event.clientY;
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
