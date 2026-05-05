import { Link, useNavigate } from 'react-router-dom'
import { useAuthStore } from '../../store/authStore'

export default function Navbar() {
  const { isAuthenticated, username, logout } = useAuthStore()
  const navigate = useNavigate()

  const handleLogout = () => {
    logout()
    navigate('/')
  }

  return (
    <nav className="bg-souls-stone border-b border-souls-gold/30">
      <div className="container mx-auto px-4 h-14 flex items-center justify-between">
        <Link to="/" className="text-souls-gold font-serif text-lg tracking-wide">
          Souls Build Planner
        </Link>

        <div className="flex items-center gap-6 text-sm">
          <Link to="/builds" className="text-stone-300 hover:text-souls-gold transition-colors">
            Builds
          </Link>

          {isAuthenticated ? (
            <>
              <span className="text-souls-ash">{username}</span>
              <button
                onClick={handleLogout}
                className="text-stone-300 hover:text-souls-gold transition-colors"
              >
                Log out
              </button>
            </>
          ) : (
            <>
              <Link to="/login" className="text-stone-300 hover:text-souls-gold transition-colors">
                Log in
              </Link>
              <Link
                to="/register"
                className="px-3 py-1 bg-souls-gold/20 border border-souls-gold/50 rounded text-souls-gold hover:bg-souls-gold/30 transition-colors"
              >
                Register
              </Link>
            </>
          )}
        </div>
      </div>
    </nav>
  )
}
