
var gl;
var points;

window.onload = function init()
{
    var canvas = document.getElementById( "gl-canvas" );
    
    gl = WebGLUtils.setupWebGL( canvas );
    if ( !gl ) { alert( "WebGL isn't available" ); }

/*  
    // Four Vertices
    
    var vertices = [
        vec2( -0.75, -0.75 ),
        vec2(  -0.75,  0.75 ),
        vec2(  0.75, 0.75 ),
        vec2( 0.75, -0.75)
    ];
*/

    //Changed to eight vertices for octagon
    var vertices = [
        vec2(0.2, 0.5),
        vec2(0.5, 0.2),
        vec2(0.5, -0.2),
        vec2(0.2, -0.5),
        vec2(-0.2, -0.5),
        vec2(-0.5, -0.2),
        vec2(-0.5, 0.2),
        vec2(-0.2, 0.5)
    ];

    //
    //  Configure WebGL
    //
    gl.viewport( 0, 0, canvas.width, canvas.height );
    gl.clearColor( 1.0, 0.0, 0.0, 1.0 );
    
    //  Load shaders and initialize attribute buffers
    
    var program = initShaders( gl, "vertex-shader", "fragment-shader" );
    gl.useProgram( program );
    
    // Load the data into the GPU
    
    var bufferId = gl.createBuffer();
    gl.bindBuffer( gl.ARRAY_BUFFER, bufferId );
    gl.bufferData( gl.ARRAY_BUFFER, flatten(vertices), gl.STATIC_DRAW );

    // Associate out shader variables with our data buffer
    
    var vPosition = gl.getAttribLocation( program, "vPosition" );
    gl.vertexAttribPointer( vPosition, 2, gl.FLOAT, false, 0, 0 );
    gl.enableVertexAttribArray( vPosition );

    render();
};


function render() {
    gl.clear( gl.COLOR_BUFFER_BIT );
    gl.drawArrays( gl.TRIANGLE_FAN, 0, 8 ); //changed to 8 to draw 8-sided shape
}
