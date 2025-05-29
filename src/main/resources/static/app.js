window.onload = function () {
  const tg = window.Telegram.WebApp;
  tg.expand();

  const initData = tg.initData;

  fetch("/auth?initData=" + encodeURIComponent(initData), { method: "POST" })
    .then((res) => res.json())
    .then((data) => {
      document.getElementById("user-card").innerHTML = `
        <div class="username">@${data.userName}</div>
        <p>Имя: ${data.firstName}</p>
        <p>Фамилия: ${data.lastName}</p>
        <p>ID: ${data.id}</p>
      `;
    })
    .catch((err) => {
      console.error(err);
      document.getElementById("user-card").innerHTML = "<p>Ошибка авторизации</p>";
    });
};
