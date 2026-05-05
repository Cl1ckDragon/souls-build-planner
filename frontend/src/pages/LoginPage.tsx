import { useForm } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import { z } from 'zod'
import { useNavigate, Link } from 'react-router-dom'
import { login } from '../api/auth'
import { useAuthStore } from '../store/authStore'
import { useState } from 'react'

const schema = z.object({
  usernameOrEmail: z.string().min(1, 'Required'),
  password: z.string().min(1, 'Required'),
})
type FormData = z.infer<typeof schema>

export default function LoginPage() {
  const navigate = useNavigate()
  const loginStore = useAuthStore((s) => s.login)
  const [serverError, setServerError] = useState<string | null>(null)

  const { register, handleSubmit, formState: { errors, isSubmitting } } = useForm<FormData>({
    resolver: zodResolver(schema),
  })

  const onSubmit = async (data: FormData) => {
    setServerError(null)
    try {
      const res = await login(data.usernameOrEmail, data.password)
      loginStore(res.data.token, res.data.username, res.data.role)
      navigate('/')
    } catch {
      setServerError('Invalid credentials.')
    }
  }

  return (
    <div className="max-w-md mx-auto mt-16">
      <h1 className="font-serif text-2xl text-souls-gold mb-8 text-center">Log In</h1>

      <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
        <div>
          <label className="block text-sm text-stone-400 mb-1">Username or Email</label>
          <input
            {...register('usernameOrEmail')}
            className="w-full bg-souls-stone border border-souls-gold/30 rounded px-3 py-2 text-stone-200 focus:outline-none focus:border-souls-gold"
          />
          {errors.usernameOrEmail && (
            <p className="text-red-400 text-xs mt-1">{errors.usernameOrEmail.message}</p>
          )}
        </div>

        <div>
          <label className="block text-sm text-stone-400 mb-1">Password</label>
          <input
            type="password"
            {...register('password')}
            className="w-full bg-souls-stone border border-souls-gold/30 rounded px-3 py-2 text-stone-200 focus:outline-none focus:border-souls-gold"
          />
          {errors.password && (
            <p className="text-red-400 text-xs mt-1">{errors.password.message}</p>
          )}
        </div>

        {serverError && <p className="text-red-400 text-sm">{serverError}</p>}

        <button
          type="submit"
          disabled={isSubmitting}
          className="w-full py-2 bg-souls-gold/20 border border-souls-gold text-souls-gold rounded hover:bg-souls-gold/30 transition-colors disabled:opacity-50"
        >
          {isSubmitting ? 'Logging in…' : 'Log In'}
        </button>
      </form>

      <p className="text-center text-sm text-souls-ash mt-6">
        No account?{' '}
        <Link to="/register" className="text-souls-gold hover:underline">
          Register
        </Link>
      </p>
    </div>
  )
}
