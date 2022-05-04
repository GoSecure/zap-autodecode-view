# Auto-Decode View for WebSocket

*Looking for a maintainer: Please open an issue to let us know if you want to adopt this project.*

This ZAP plugin is a demonstration of a custom view for the WebSocket messages.
It adds a view "Auto-Decode" to the existing list "Text" and "Hex".

This view will decode automatically messages if it detect one of the following compression or encoding algorithms:
 - ZIP
 - LZIP
 - Base64
 - URL decoding 
 - Brotli

# Tutorials

 - [How to install](https://github.com/GoSecure/zap-autodecode-view/wiki#how-to-install)
 - [How to use](https://github.com/GoSecure/zap-autodecode-view/wiki#how-to-use)
