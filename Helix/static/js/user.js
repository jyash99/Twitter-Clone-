$(".follow-member").click(function() {
    var memberId = this.getAttribute("member-id");
    var button = this;
    debugger;
    var isFollowed = $(button).hasClass("followed-button");
    if(isFollowed) {
        $.ajax({
            type: "POST",
            url: "/users/un-follow-member/" + memberId,
            success: function(response) {
                $(button).removeClass("followed-button");
                button.querySelector("span").innerText = "Follow";
                button.style.backgroundColor = "#55abee";
            },
            contentType: "application/json"
        });
    } else {
        $.ajax({
            type: "POST",
            url: "/users/follow-member/" + memberId,
            success: function(response) {
                $(button).addClass("followed-button");
                button.querySelector("span").innerText = "Followed";
                button.style.backgroundColor = "grey";
            },
            contentType: "application/json"
        });
    }
});