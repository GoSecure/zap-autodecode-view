# Auto-Decode View for WebSocket

This ZAP plugin is a demonstration of a custom view for the WebSocket messages.
It adds a view "Auto-Decode" to the existing list "Text" and "Hex".

This view will decode automatically messages if it detect one of the following compression or encoding algorithms:
 - ZIP
 - LZIP
 - Base64
 - URL decoding 
 - Brotli
