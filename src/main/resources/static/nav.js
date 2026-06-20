

(function () {

  if (!document.getElementById('hg-nav-style')) {
    const style = document.createElement('style');
    style.id = 'hg-nav-style';
    style.textContent = `
      .nav-logo {
        width: 300px !important;
        height: 300px !important;
        object-fit: contain;
      }

      .hamburger {
        display: none;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        gap: 5px;
        width: 38px;
        height: 38px;
        min-width: 38px;
        background: rgba(255,255,255,0.15);
        border: 1.5px solid rgba(255,255,255,0.6);
        border-radius: 8px;
        cursor: pointer;
        z-index: 1100;
        transition: background 0.2s;
        flex-shrink: 0;
        padding: 0;
      }
      .hamburger:hover { background: rgba(107,33,168,0.25); }
      .hamburger span {
        display: block;
        width: 20px;
        height: 2px;
        background: #ffffff;
        border-radius: 2px;
        transition: all 0.3s;
      }
      nav.scrolled .hamburger span { background: #6B21A8; }
      .hamburger.open span:nth-child(1) { transform: translateY(7px) rotate(45deg); }
      .hamburger.open span:nth-child(2) { opacity: 0; }
      .hamburger.open span:nth-child(3) { transform: translateY(-7px) rotate(-45deg); }

      .mobile-menu {
        display: none;
        position: fixed;
        top: 64px; left: 0; right: 0; bottom: 0;
        background: rgba(26,10,46,0.97);
        backdrop-filter: blur(12px);
        -webkit-backdrop-filter: blur(12px);
        z-index: 999;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        gap: 0.4rem;
        opacity: 0;
        pointer-events: none;
        transition: opacity 0.3s;
        overflow-y: auto;
        padding: 1.5rem 0 2rem 0;
      }
      .mobile-menu.open {
        opacity: 1;
        pointer-events: all;
        display: flex;
      }
      .mobile-menu a {
        color: rgba(255,255,255,0.85);
        font-size: 1rem;
        padding: 12px 24px;
        border-radius: 12px;
        text-decoration: none;
        width: 240px;
        text-align: center;
        transition: all 0.2s;
        font-family: 'DM Sans', sans-serif;
      }
      .mobile-menu a:hover { background: rgba(107,33,168,0.25); color: #fff; }

      .mobile-menu .mob-sos {
        background: #e84646;
        color: #fff !important;
        font-weight: 700;
        border-radius: 30px;
        animation: mob-pulse 2s infinite;
      }
      @keyframes mob-pulse {
        0%,100% { box-shadow: 0 0 0 0 rgba(232,70,70,0.5); }
        50%      { box-shadow: 0 0 0 8px rgba(232,70,70,0); }
      }
      .mobile-menu .mob-login {
        border: 1px solid rgba(107,33,168,0.3);
        color: #c084fc !important;
        border-radius: 30px;
      }
      .mobile-menu .mob-register {
        background: linear-gradient(135deg, #6B21A8 0%, #EC4899 100%);
        color: #fff !important;
        font-weight: 700;
        border-radius: 30px;
      }
      .mobile-menu .mob-dashboard {
        background: rgba(107,33,168,0.2);
        color: #c084fc !important;
        border-radius: 30px;
      }
      .mobile-menu-divider {
        width: 240px;
        height: 1px;
        background: rgba(255,255,255,0.1);
        margin: 0.2rem 0;
        flex-shrink: 0;
      }

      @media (max-width: 1024px) {
        .nav-links { display: none !important; }
        .hamburger { display: flex !important; }

        nav {
          padding: 0 1rem !important;
        }
      }

      @media (max-width: 360px) {
        nav {
          padding: 0 0.5rem !important;
          height: 56px !important;
          display: flex !important;
          flex-direction: row !important;
          align-items: center !important;
          justify-content: space-between !important;
          box-sizing: border-box !important;
          width: 100% !important;
          /* overflow hidden असेल तर hamburger clip होतो — म्हणून visible ठेवा */
          overflow: visible !important;
        }
        .nav-logo {
          width: 200px !important;
          height: 200px !important;
          flex-shrink: 0 !important;
        }
        .hamburger {
          display: flex !important;
          visibility: visible !important;
          opacity: 1 !important;
          width: 34px !important;
          min-width: 34px !important;
          height: 34px !important;
          flex-shrink: 0 !important;
          position: relative !important;
          z-index: 1100 !important;
        }
        .hamburger span {
          width: 18px !important;
        }
        .mobile-menu {
          top: 56px !important;
        }
        .mobile-menu a {
          font-size: 0.9rem !important;
          padding: 10px 16px !important;
          width: 200px !important;
        }
        .mobile-menu-divider {
          width: 200px !important;
        }
      }
    `;
    document.head.appendChild(style);
  }

  const nav = document.querySelector('nav');
  if (!nav) return;

  if (document.getElementById('hamburger')) return;

  const btn = document.createElement('button');
  btn.className = 'hamburger';
  btn.id = 'hamburger';
  btn.setAttribute('aria-label', 'Toggle menu');
  btn.innerHTML = '<span></span><span></span><span></span>';
  nav.appendChild(btn);

  const isLoggedIn = !!sessionStorage.getItem('userId');
  const menu = document.createElement('div');
  menu.className = 'mobile-menu';
  menu.id = 'mobileMenu';

  if (isLoggedIn) {
    menu.innerHTML = `
      <a href="sos.html" class="mob-sos">🚨 SOS Emergency</a>
      <div class="mobile-menu-divider"></div>
      <a href="dashboard.html" class="mob-dashboard">📊 Dashboard</a>
      <a href="start-walk.html">🚶‍♀️ Start Walk</a>
      <a href="tracking.html">📍 Live Tracking</a>
      <a href="contacts.html">👥 My Contacts</a>
      <a href="walk-history.html">📄 Walk History</a>
      <a href="nearby.html">🏥 Nearby Help</a>
      <a href="Complaint.html">📝 File Report</a>
      <a href="profile.html">👤 My Profile</a>
      <div class="mobile-menu-divider"></div>
      <a href="#" class="mob-login" id="mob-logout-btn">🚪 Logout</a>
    `;
  } else {
    menu.innerHTML = `
      <a href="sos.html" class="mob-sos">🚨 SOS Emergency</a>
      <div class="mobile-menu-divider"></div>
      <a href="nearby.html">🏥 Nearby Help</a>
      <a href="about.html">ℹ️ About</a>
      <div class="mobile-menu-divider"></div>
      <a href="login.html" class="mob-login">🔐 Login</a>
      <a href="register.html" class="mob-register">🛡️ Register</a>
    `;
  }

  document.body.appendChild(menu);


  btn.addEventListener('click', () => {
    btn.classList.toggle('open');
    menu.classList.toggle('open');
    document.body.style.overflow = menu.classList.contains('open') ? 'hidden' : '';
  });

  menu.querySelectorAll('a').forEach(link => {
    link.addEventListener('click', () => {
      btn.classList.remove('open');
      menu.classList.remove('open');
      document.body.style.overflow = '';
    });
  });

  const mobLogout = document.getElementById('mob-logout-btn');
  if (mobLogout) {
    mobLogout.addEventListener('click', (e) => {
      e.preventDefault();
      sessionStorage.removeItem('userId');
      sessionStorage.removeItem('fullName');
      sessionStorage.removeItem('token');
      window.location.href = 'index.html';
    });
  }

  window.addEventListener('scroll', () => {
    nav.classList.toggle('scrolled', window.scrollY > 20);
  });

})();