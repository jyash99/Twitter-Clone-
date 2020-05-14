<html>
    <body>
        <h1>User Login</h1>
        <div id="time">
        </div>
        <br><br>
        <div>
            <marquee>Made with love by Yash Jain.</marquee>
        </div>
        <br><br>
        <form>
            <input type="text" placeholder="Enter username">
            <button type="submit">Submit</button>
        </form>
        <script type="text/javascript">
            function updateTime() {
                document.getElementById("time").innerText = new Date().toString();
            }
                setInterval(updateTime, 1000);
        </script>
    </body>
</html>